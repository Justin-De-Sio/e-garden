<template>
        <UPopover :popper="{ arrow: true }" >
            <UButton color="white" trailing-icon="i-heroicons-plus" :ui="{rounded: 'rounded-lg'}" />

            <template #panel >
            <div class="px-8 py-4">
                <Placeholder class="h-20 w-48" />

                <div class="wrapper_report">
                    <ul>
                        <UDivider label="REPORT" />
                        <li class="eachReport">
                            <div class="wrapperEach">
                                <p>Mon Contenu</p>
                                <p>La Date</p>
                                <button type="button" class="editReport" >Modifier</button>
                            </div>
                            <UDivider />
                        </li>
                    </ul>
                </div>

                <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
                    <UFormGroup label="Contenu" name="password">
                    <UTextarea v-model="state.password" type="text" />
                    </UFormGroup>

                    <UButton type="submit">
                        Envoyer
                    </UButton>
                    <UButton type="submit">
                        Brouillon
                    </UButton>
                </UForm>
            </div>
            </template>
        </UPopover>
</template>

<script setup lang="ts">
import { z } from 'zod'
import type { FormSubmitEvent } from '#ui/types'
import { callAPI } from '~/services/callAPI'

const api = new callAPI;



const schema = z.object({
  password: z.string().min(8, 'Must be at least 8 characters')
})

type Schema = z.output<typeof schema>

const state = reactive({
  email: undefined,
  password: undefined
})

onMounted(() => {

})

async function onSubmit() {

    const response = await api.fetchAPIGetPaginated("myNotValidatedReports", 0, 2);

    console.log(response)

}
</script>

<style scoped>

.wrapperEach{
    display: flex;
    gap: 5rem;
    margin-bottom: 1rem;
}

.eachReport{
    margin: 1rem 0 ;
}

.editReport{
    cursor: pointer;
    background: none;
    border: none;
    padding: 0.3rem 0.7rem;
    background-color: #f4a261 ;
    color: white;
    border-radius: 0.4rem;
    font-size: clamp(0.7rem, 2vw, 0.9rem); 
}

</style>