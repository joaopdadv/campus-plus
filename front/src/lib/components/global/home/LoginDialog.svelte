<script lang="ts">
    import { createForm } from "felte";
    import { validator } from "@felte/validator-zod";
    import { z } from "zod";

    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import Button from "$lib/components/ui/button/button.svelte";
    import Input from "$lib/components/ui/input/input.svelte";
    import Label from "$lib/components/ui/label/label.svelte";

    import { goto } from "$app/navigation";
    import { login } from "$lib/api/auth/authApi";
    import { browser } from "$app/environment";
    import { writable } from "svelte/store";

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

<Dialog.Root>
    <Dialog.Trigger>
        <div class="p-4 bg-blue-500 text-white rounded-md cursor-pointer">
            Login Dialog
        </div>
    </Dialog.Trigger>

    <Dialog.Content class="sm:max-w-[425px]">
        <Dialog.Header>
            <Dialog.Title>Acessar Plataforma</Dialog.Title>
            <Dialog.Description>
                Entre com seu e-mail e senha para continuar.
            </Dialog.Description>
        </Dialog.Header>

        <form use:form class="grid gap-4 py-4">
            <div class="grid w-full max-w-sm items-center gap-1.5">
                <Label for="email">Email</Label>
                <Input
                    id="email"
                    type="email"
                    name="email"
                    placeholder="seu@email.com"
                    aria-invalid={$errors.email ? "true" : undefined}
                />
                {#if $errors.email}
                    <p class="text-sm text-red-500">{$errors.email[0]}</p>
                {/if}
            </div>
            <div class="grid w-full max-w-sm items-center gap-1.5">
                <Label for="password">Senha</Label>
                <Input
                    id="password"
                    type="password"
                    name="password"
                    placeholder="Sua senha"
                    aria-invalid={$errors.password ? "true" : undefined}
                />
                {#if $errors.password}
                    <p class="text-sm text-red-500">{$errors.password[0]}</p>
                {/if}
            </div>

            {#if $serverError}
                <p class="text-sm text-red-500">{$serverError}</p>
            {/if}

            <Dialog.Footer>
                <Button type="submit" class="w-full" disabled={$isSubmitting}>
                    {#if $isSubmitting}
                        Entrando...
                    {:else}
                        Entrar
                    {/if}
                </Button>
            </Dialog.Footer>
        </form>
    </Dialog.Content>
</Dialog.Root>
