package kr.hs.b1nd.intern.mentomen.util

data class TagState(
    var isDesignChecked: Boolean = true,
    var isWebChecked: Boolean = true,
    var isAndroidChecked: Boolean = true,
    var isServerChecked: Boolean = true,
    var isiOSChecked: Boolean = true
) {

    fun setDesignTrue() {
        isDesignChecked = true
        isWebChecked = false
        isAndroidChecked = false
        isServerChecked = false
        isiOSChecked = false
    }

    fun setWebTrue() {
        isDesignChecked = false
        isWebChecked = true
        isAndroidChecked = false
        isServerChecked = false
        isiOSChecked = false
    }

    fun setServerTrue() {
        isDesignChecked = false
        isWebChecked = false
        isAndroidChecked = false
        isServerChecked = true
        isiOSChecked = false
    }

    fun setAndroidTrue() {
        isDesignChecked = false
        isWebChecked = false
        isAndroidChecked = true
        isServerChecked = false
        isiOSChecked = false
    }

    fun setIosTrue() {
        isDesignChecked = false
        isWebChecked = false
        isAndroidChecked = false
        isServerChecked = false
        isiOSChecked = true
    }

    fun setAllTrue() {
        isAndroidChecked = true
        isWebChecked = true
        isAndroidChecked = true
        isServerChecked = true
        isiOSChecked = true
    }


}
