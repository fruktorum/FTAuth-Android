package com.fruktorum.ftauth.data.phoneNumber

sealed class PhoneMask {
    object X_XXX_XXX_XXXX : PhoneMask()
    object XX_XXX_XXX_XXXX : PhoneMask()
    class CustomMask(val mask: String) : PhoneMask()
    object NONE : PhoneMask()
}