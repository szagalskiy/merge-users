package com.development.merge

/**
 * Service contains merging user's mails logic
 */
class MergeService {

    private val mailIndex : HashMap<String, HashSet<String>> = hashMapOf()
    private val usersIndex : HashMap<String, User> = hashMapOf()

    /**
     * Adding user
     * @param user object of class {@link User}
     */
    fun add(user : User) {
        usersIndex.put(user.name, user)
        for (mail in user.mails) {
            mailIndex.putIfAbsent(mail, hashSetOf())
            mailIndex[mail]!!.add(user.name)
        }
    }

    /**
     * Returns list of users with merged mails
     * @return list of objects {@link User}
     */
    fun getResult() : List<User> {
        usersIndex.values.forEach { it.processed = false }
        val mergedUserList : MutableList<User> = arrayListOf()
        for ((_, user) in usersIndex) {
            if (!user.processed) {
                mergedUserList.add(User(user.name, findAllMails(user.mails), true))
                user.processed = true
            }
        }
        return mergedUserList
    }

    /**
     * Finding all mails, that belong to user
     * @param mails set of mails
     */
    private fun findAllMails(mails : Set<String>) : HashSet<String> {
        val allMails : HashSet<String> = hashSetOf()
        val unprocessedMails : HashSet<String> = mails.toHashSet()
        while (unprocessedMails.isNotEmpty()) {
            val mail = unprocessedMails.first()
            unprocessedMails.remove(mail)
            if (!allMails.contains(mail)) {
                val users = mailIndex[mail]!!.map { usersIndex[it]!! }
                for (user in users) {
                    if (!user.processed) {
                        user.processed = true
                        user.mails
                                .filter { !allMails.contains(it) }
                                .let { unprocessedMails.addAll(it) }
                    }
                }
                allMails.add(mail)
            }
        }

        return allMails
    }
}