import { writable } from "svelte/store";

export const toasts = writable<IToast[]>([]);

export interface IToast {
    id?: number;
    type?: string;
    message: string;
    dismissible?: boolean;
    timeout?: number;
}

export const addToast = (toast: IToast) => {
    // Create a unique ID so we can easily find/remove it
    // if it is dismissible/has a timeout.
    const id = Math.floor(Math.random() * 10000);

    // Setup some sensible defaults for a toast.
    const defaults = {
        id,
        type: "info",
        dismissible: false,
        timeout: 3000,
    };

    // Push the toast to the top of the list of toasts
    toasts.update((all) => [{ ...defaults, ...toast }, ...all]);

    // If toast is dismissible, dismiss it after "timeout" amount of time.
    if (toast.timeout) setTimeout(() => dismissToast(id), toast.timeout);
};

export const dismissToast = (id: number) => {
    toasts.update((all) => all.filter((t) => t.id !== id));
};
