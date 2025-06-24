<script lang="ts">
    import type { PageData } from "./$types";
    import DialogManager from "./(components)/DialogManager.svelte";
    import LoginForm from "./(components)/LoginForm.svelte";

    let { data }: { data: PageData } = $props();
</script>

<DialogManager {data} />
