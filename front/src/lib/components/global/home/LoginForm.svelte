<script lang="ts">
    import { createForm } from "felte";
    import { validator } from "@felte/validator-zod";
    import { z } from "zod";

    import Button from "$lib/components/ui/button/button.svelte";
    import Input from "$lib/components/ui/input/input.svelte";

    import { goto } from "$app/navigation";
    import { login } from "$lib/api/auth/authApi";
    import { browser } from "$app/environment";
    import { writable } from "svelte/store";
    import Controller from "../forms/Controller.svelte";
    import FormError from "../forms/FormError.svelte";
    import { addToast } from "$lib/store/toast";

    const serverError = writable("");

    const schema = z.object({
        email: z.string().email("Por favor, insira um e-mail válido."),
        password: z.string().min(1, "A senha não pode estar em branco."),
    });

    const { form, errors, isSubmitting } = browser
        ? createForm({
              onSubmit: async (values) => {
                  try {
                      await login(values);
                      await goto("/dashboard");
                  } catch (error) {
                      if (error instanceof Error) {
                          serverError.set(
                              error.message ||
                                  "Ocorreu um erro. Tente novamente.",
                          );
                          addToast({
                              message:
                                  error.message ||
                                  "Ocorreu um erro. Tente novamente.",
                              type: "error",
                              timeout: 3000,
                          });
                      } else {
                          serverError.set(
                              "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                          );
                      }
                  }
              },
              extend: validator({ schema }),
          })
        : {
              form: () => {},
              errors: writable({}),
              isSubmitting: writable(false),
          };
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
            placeholder="Sua senha"
            aria-invalid={$errors.password ? "true" : undefined}
        />
    </Controller>

    <FormError message={$serverError} />

    <Button type="submit" class="w-full" disabled={$isSubmitting}>
        {#if $isSubmitting}
            Entrando...
        {:else}
            Entrar
        {/if}
    </Button>
</form>
