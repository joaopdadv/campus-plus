<script lang="ts">
    import * as Form from "$lib/components/ui/form/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import {
        type SuperValidated,
        type Infer,
        superForm,
    } from "sveltekit-superforms";
    import { zodClient } from "sveltekit-superforms/adapters";
    import { toast } from "svelte-sonner";
    import {
        registerSchema,
        type RegisterSchema,
    } from "../(schema)/registerSchema";
    import Checkbox from "$lib/components/ui/checkbox/checkbox.svelte";
    import { USER_ROLE } from "$lib/enums/auth";

    let {
        data,
        onSuccess,
    }: {
        data: { registerForm: SuperValidated<Infer<RegisterSchema>> };
        onSuccess?: () => void;
    } = $props();

    const form = superForm(data.registerForm, {
        validators: zodClient(registerSchema),
        onUpdated: ({ form: f }) => {
            if (f.valid) {
                toast.success(
                    "Cadastro realizado com sucesso! Para continuar, faça login.",
                );
                if (onSuccess) {
                    onSuccess();
                }

                //TODO: ao invés de redirecionar ou fechar, adicionar state de sucesso no form que mostra uma mensagem de sucesso com icone de check
            } else {
                if (f.errors?._errors?.length) {
                    toast.error(f.errors._errors[0]);
                } else if (Object.keys(f.errors).length > 0) {
                    toast.error("Por favor, verifique os campos destacados.");
                }
            }
        },
    });

    const { form: formData, enhance } = form;
</script>

<form method="POST" use:enhance action="?/register">
    <Form.Field {form} name="email">
        <Form.Control>
            {#snippet children({ props })}
                <Form.Label>Email</Form.Label>
                <Input
                    {...props}
                    bind:value={$formData.email}
                    placeholder="exemplo@email.com"
                />
            {/snippet}
        </Form.Control>
        <Form.FieldErrors />
    </Form.Field>
    <Form.Field {form} name="password">
        <Form.Control>
            {#snippet children({ props })}
                <Form.Label>Password</Form.Label>
                <Input
                    type="password"
                    placeholder="Insira sua senha"
                    {...props}
                    bind:value={$formData.password}
                />
            {/snippet}
        </Form.Control>
        <Form.FieldErrors />
    </Form.Field>
    <Form.Field {form} name="confirmPassword">
        <Form.Control>
            {#snippet children({ props })}
                <Form.Label>Confirmar Senha</Form.Label>
                <Input
                    type="password"
                    placeholder="Confirme sua senha"
                    {...props}
                    bind:value={$formData.confirmPassword}
                />
            {/snippet}
        </Form.Control>
        <Form.FieldErrors />
    </Form.Field>
    <Form.Field {form} name="role">
        <Form.Control>
            {#snippet children({ props })}
                <Checkbox
                    {...props}
                    checked={$formData.role === USER_ROLE.PROFESSOR}
                    value={$formData.role.toString()}
                    onCheckedChange={(v) => {
                        $formData.role = v
                            ? USER_ROLE.PROFESSOR
                            : USER_ROLE.ALUNO;
                    }}
                />
                <Form.Label>Professor</Form.Label>
            {/snippet}
        </Form.Control>
        <Form.FieldErrors />
    </Form.Field>
    <Form.Button class="w-full mt-2">Entrar</Form.Button>
</form>
