package edu.oregonstate.cs492.eventsearchwithnavui.data

class BookmarkedReposRepository(
    private val dao: EventFinderRepoDao
) {
    suspend fun insertBookmarkedRepo(repo: EventFinderListJson) = dao.insert(repo)
    suspend fun deleteBookmarkedRepo(repo: EventFinderListJson) = dao.delete(repo)
    fun getAllBookmarkedRepos() = dao.getAllRepos()
    fun getBookmarkedRepoByName(name: String) = dao.getRepoByName(name)
}