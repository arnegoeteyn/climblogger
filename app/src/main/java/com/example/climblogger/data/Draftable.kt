package com.example.climblogger.data


abstract class Draftable<Me : Draftable<Me>> {

    abstract fun toDraft(): Draft<Me>

    abstract class Draft<Me : Draftable<Me>> {
        fun fromDraft(): Me? {
            if (javaClass.fields.all {
                    // check that the value isn't null or that we allow it to be null
                    it.get(this) != null || it.isAnnotationPresent(NullableOutDraft::class.java)
                }) {
                return this.unwrapDraft()
            }
            return null
        }

        protected abstract fun unwrapDraft(): Me
    }

}

/**
 * Shows that the field can also be nullable in the non-draft
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class NullableOutDraft

