import type { PageServerLoad } from "./$types.js";
import { setError, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { loginSchema } from "./(schema)/loginSchema";
import type { Actions } from "../$types.js";
import { fail } from "@sveltejs/kit";
import { toast } from "svelte-sonner";
import { login } from "$lib/api/auth/authApi.js";

export const load: PageServerLoad = async () => {
    return {
        loginForm: await superValidate(zod(loginSchema)),
    };
};

export const actions: Actions = {
    login: async (event) => {
        const form = await superValidate(event, zod(loginSchema));
        if (!form.valid) {
            return fail(400, {
                form,
            });
        }

        try {
            await login(form.data);

            return {
                form,
                message: "Login realizado com sucesso!"
            };

        } catch (error) {
            if (error instanceof Error) {
                console.error("Erro na API de login:", error.message);
                setError(form, "", error.message);
                return fail(500, { form, message: error.message });
            } else {
                console.error("Erro na API de login:", error);
                setError(form, "", "Erro interno, não foi possível fazer o login.");
                return fail(500, { form, message: "Erro interno, não foi possível fazer o login." });
            }
        }
    },
};