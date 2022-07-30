package com.example.mediaplayer69.data

class SearchInfoRepository(
    private val dao: SearchDAO
) {
    suspend fun insertSearchInfo(searchInfo: SearchInfo) = dao.insert(searchInfo)
    suspend fun deleteSearchInfo(searchInfo: SearchInfo) = dao.delete(searchInfo)

    fun getSearchInfoRepos() = dao.getSearchInfo()
}