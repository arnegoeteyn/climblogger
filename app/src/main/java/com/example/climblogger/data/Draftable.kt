package com.example.climblogger.data


interface Draftable<Me : Draftable<Me>> {

    fun toDraft(): Draft<Me>

    interface Draft<Me : Draftable<Me>> {
        fun fromDraft(): Draftable<Me>?
    }

}

/**
 * Shows that the field can also be nullable in the non-draft
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class NullableOutDraft

