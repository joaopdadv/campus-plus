<script lang="ts">
    import Navbar from "$lib/components/global/home/Navbar.svelte";
    import "../../app.css";

    let { children } = $props();
</script>

<!-- <Navbar /> -->

{@render children()}
