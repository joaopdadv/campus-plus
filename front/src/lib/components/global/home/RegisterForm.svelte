<script lang="ts">
    import { createForm } from "felte";
    import { z } from "zod";
    import { writable } from "svelte/store";
    import { browser } from "$app/environment";
    import { goto } from "$app/navigation";

    import Button from "$lib/components/ui/button/button.svelte";
    import Input from "$lib/components/ui/input/input.svelte";
    import Controller from "../forms/Controller.svelte";
    import FormError from "../forms/FormError.svelte";
    import { addToast } from "$lib/store/toast";
    import { register } from "$lib/api/auth/authApi";
    import { USER_ROLE } from "$lib/enums/auth";
    import Checkbox from "$lib/components/ui/checkbox/checkbox.svelte";
    import { validator } from "@felte/validator-zod";

    const serverError = writable("");

    const schema = z
        .object({
            email: z.string().email("Por favor, insira um e-mail válido."),
            password: z
                .string()
                .min(6, "A senha deve ter no mínimo 6 caracteres."),
            confirmPassword: z
                .string()
                .min(6, "A confirmação de senha é obrigatória."),
            role: z.nativeEnum(USER_ROLE),
        })
        .superRefine(({ password, confirmPassword }, ctx) => {
            if (password !== confirmPassword) {
                ctx.addIssue({
                    code: "custom",
                    message: "As senhas não coincidem.",
                    path: ["confirmPassword"],
                });
            }
        });

    type FormSchema = z.infer<typeof schema>;
    type Errors = Partial<Record<keyof FormSchema, string[] | null>>;

    const initialValues: FormSchema = {
        email: "",
        password: "",
        confirmPassword: "",
        role: USER_ROLE.ALUNO,
    };

    const { form, data, errors, isSubmitting, setData, setErrors } = browser
        ? createForm<FormSchema>({
              initialValues,
              onSubmit: async (values) => {
                  serverError.set("");

                  try {
                      await register(values);
                      addToast({
                          message: "Registro realizado com sucesso!",
                          type: "success",
                      });
                      await goto("/dashboard");
                  } catch (error) {
                      const message =
                          error instanceof Error
                              ? error.message
                              : "Ocorreu um erro inesperado. Tente novamente.";
                      serverError.set(message);
                      addToast({
                          message,
                          type: "error",
                          timeout: 3000,
                      });
                  }
              },
              extend: validator({ schema }),
          })
        : {
              form: () => {},
              data: writable<FormSchema>(initialValues),
              errors: writable<Errors>({}),
              isSubmitting: writable(false),
              setData: (() => {}) as any,
              setErrors: (() => {}) as any,
          };

    function handleRoleChange(isChecked: boolean) {
        setData("role", isChecked ? USER_ROLE.PROFESSOR : USER_ROLE.ALUNO);
    }
</script>

<form use:form class="grid gap-4 py-4">
    <Controller label="Email" error={$errors.email?.[0]} let:id>
        <Input
            {id}
            type="email"
            name="email"
            placeholder="seu@email.com"
            aria-invalid={$errors.email ? "true" : undefined}
        />
    </Controller>

    <Controller label="Senha" error={$errors.password?.[0]} let:id>
        <Input
            {id}
            type="password"
            name="password"
            placeholder="Crie uma senha"
            aria-invalid={$errors.password ? "true" : undefined}
        />
    </Controller>

    <Controller
        label="Confirmar Senha"
        error={$errors.confirmPassword?.[0]}
        let:id
    >
        <Input
            {id}
            type="password"
            name="confirmPassword"
            placeholder="Confirme sua senha"
            aria-invalid={$errors.confirmPassword ? "true" : undefined}
        />
    </Controller>

    <div class="flex items-center space-x-2">
        <label class="flex items-center space-x-2 cursor-pointer">
            <Checkbox
                id="role"
                name="role"
                aria-label="Selecionar papel de professor"
                class="peer"
                checked={$data.role === USER_ROLE.PROFESSOR}
                onCheckedChange={handleRoleChange}
            />
            <span
                class="text-sm font-medium peer-disabled:opacity-50 peer-disabled:cursor-not-allowed"
            >
                Professor
            </span>
        </label>
    </div>

    <FormError message={$serverError} />

    <Button type="submit" class="w-full" disabled={$isSubmitting}>
        {#if $isSubmitting}
            Registrando...
        {:else}
            Criar conta
        {/if}
    </Button>
</form>
