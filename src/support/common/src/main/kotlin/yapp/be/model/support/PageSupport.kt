package yapp.be.model.support

data class PagedResult<T>(
    val pageNumber: Int,
    val pageSize: Int,
    val content: List<T>
)
