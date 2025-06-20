<script lang="ts">
    import LoginDialog from "$lib/components/global/home/LoginDialog.svelte";
</script>

<p>login</p>

<div
    class="container mx-auto flex h-screen flex-col items-center justify-center space-y-4"
>
    <h1 class="text-3xl font-bold">Bem-vindo ao Meu Site</h1>
    <p>Clique no botão abaixo para fazer o login.</p>

    <LoginDialog />
</div>
