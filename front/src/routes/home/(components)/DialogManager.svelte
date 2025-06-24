<script lang="ts">
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { buttonVariants } from "$lib/components/ui/button/button.svelte";
    import LoginForm from "./LoginForm.svelte";
    import type { PageData } from "../$types";

    let { data }: { data: PageData } = $props();
</script>

<Dialog.Root>
    <Dialog.Trigger class={buttonVariants({ variant: "default" })}
        >Entrar</Dialog.Trigger
    >

    <Dialog.Content
        class="sm:max-w-[425px]"
        tabindex={-1}
        onOpenAutoFocus={(event) => {
            event.preventDefault();
        }}
    >
        <Dialog.Header>
            <Dialog.Title>Acessar Plataforma</Dialog.Title>
            <Dialog.Description
                >Entre com seu e-mail e senha para continuar.</Dialog.Description
            >
        </Dialog.Header>

        <LoginForm {data} />
        <!-- <RegisterForm /> -->

        <p>
            Ainda não tem uma conta? <strong
                class="cursor-pointer hover:text-primary/70 text-primary transition"
                >Cadastre-se</strong
            >
        </p>
    </Dialog.Content>
</Dialog.Root>
