package yapp.be.domain.common.model

interface AlimtalkMessageGenerateFunction {
    fun getMessage(variables: Map<String, String>): String
}
