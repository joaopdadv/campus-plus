<script lang="ts">
    import { onMount } from "svelte";
    import type { HTMLAttributes } from "svelte/elements";

    const generateId = () =>
        `field-${Math.random().toString(36).substring(2, 9)}`;

    export let label: string | undefined = undefined;
    export let error: string | undefined = undefined;
    export let id: string | undefined = undefined;

    onMount(() => {
        if (!id) {
            id = generateId();
        }
    });

    let className: HTMLAttributes<HTMLElement>["class"] = undefined;
    export { className as class };
</script>

<div class="grid w-full max-w-sm items-center gap-1.5 {className}">
    {#if label && id}
        <label
            for={id}
            class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
            class:text-red-500={error}
        >
            {label}
        </label>
    {/if}

    {#if id}
        <slot {id} />
    {/if}

    {#if error}
        <p class="text-sm font-medium text-red-500">{error}</p>
    {/if}
</div>
