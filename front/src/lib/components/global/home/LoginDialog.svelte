<script lang="ts">
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import Button from "$lib/components/ui/button/button.svelte";
    import Input from "$lib/components/ui/input/input.svelte";
    import Label from "$lib/components/ui/label/label.svelte";

    // 1. Importe sua função de login e os tipos necessários
    import type { ILoginResponse } from "$lib/types/auth";
    import { goto } from "$app/navigation"; // Para redirecionar após o login
    import { login } from "$lib/api/auth/authApi";

    // 2. Crie variáveis para gerenciar o estado do formulário
    let email = "";
    let password = "";
    let isLoading = false;
    let errorMessage = "";

    // 3. Crie a função para manipular o login
    async function handleLogin() {
        if (!email || !password) {
            errorMessage = "Por favor, preencha o e-mail e a senha.";
            return;
        }

        isLoading = true;
        errorMessage = "";

        try {
            // 4. Chame a função da API
            const response: ILoginResponse = await login({ email, password });

            // 5. Lide com o sucesso
            // Por exemplo, armazene o token e redirecione o usuário
            // localStorage.setItem('authToken', response.token);
            console.log("Login bem-sucedido:", response);

            // Redireciona para o dashboard ou outra página protegida
            await goto("/dashboard");
        } catch (error) {
            // 6. Lide com o erro
            console.error("Erro no login:", error);
            errorMessage =
                "Credenciais inválidas ou erro no servidor. Tente novamente.";
        } finally {
            isLoading = false;
        }
    }
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

        <form on:submit|preventDefault={handleLogin} class="grid gap-4 py-4">
            <div class="grid w-full max-w-sm items-center gap-1.5">
                <Label for="email">Email</Label>
                <div class="relative">
                    <Input
                        id="email"
                        type="email"
                        placeholder="seu@email.com"
                        class="pl-8"
                        bind:value={email}
                        disabled={isLoading}
                    />
                </div>
            </div>
            <div class="grid w-full max-w-sm items-center gap-1.5">
                <Label for="password">Senha</Label>
                <div class="relative">
                    <Input
                        id="password"
                        type="password"
                        placeholder="Sua senha"
                        class="pl-8"
                        bind:value={password}
                        disabled={isLoading}
                    />
                </div>
            </div>

            {#if errorMessage}
                <p class="text-sm text-red-500">{errorMessage}</p>
            {/if}

            <Dialog.Footer>
                <Button type="submit" class="w-full" disabled={isLoading}>
                    {#if isLoading}
                        Entrando...
                    {:else}
                        Entrar
                    {/if}
                </Button>
            </Dialog.Footer>
        </form>
    </Dialog.Content>
</Dialog.Root>
