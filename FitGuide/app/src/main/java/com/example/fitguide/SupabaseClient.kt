package com.example.fitguide

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    private const val SUPABASE_URL = "SUA_URL_SUPABASE"
    private const val SUPABASE_KEY = "SUA_CHAVE_SUPABASE"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(GoTrue)
    }
}