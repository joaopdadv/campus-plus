<script lang="ts">
	import Toasts from "$lib/components/global/toast/Toasts.svelte";
	import "../app.css";

	let { children } = $props();
</script>

<Toasts />

{@render children()}
