package com.development.merge

import org.junit.jupiter.api.Test

class MergeServiceTest {

    private lateinit var mergeService : MergeService

    @Test
    fun testEmptyInput() {
        mergeService = MergeService()
        assert(mergeService.getResult().isEmpty())
    }

    @Test
    fun testFiveUsersChainTwoChains() {
        val resultMap : HashMap<String, HashSet<String>> = hashMapOf()
        resultMap.put("user1", hashSetOf("aaa@bbb.ru", "ups@pisem.net", "lol@mail.ru", "xxx@ya.ru", "foo@gmail.com"))
        resultMap.put("user5", hashSetOf("vasya@pupkin.com", "xyz@pisem.net"))

        mergeService = MergeService()
        mergeService.add(User("user1", hashSetOf("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")))
        mergeService.add(User("user2", hashSetOf("foo@gmail.com", "ups@pisem.net")))
        mergeService.add(User("user3", hashSetOf("xyz@pisem.net", "vasya@pupkin.com")))
        mergeService.add(User("user4", hashSetOf("ups@pisem.net", "aaa@bbb.ru")))
        mergeService.add(User("user5", hashSetOf("xyz@pisem.net")))

        val result = mergeService.getResult()
        assert(result.size == 2)
        for (user in result) {
            assert(resultMap.get(user.name)!!.equals(user.mails))
        }
    }

    @Test
    fun testOneUserOneMail() {
        mergeService = MergeService()
        mergeService.add(User("user1", hashSetOf("mail1")))
        assert(mergeService.getResult().size == 1)
    }

    @Test
    fun testOneUserTwoMails() {
        mergeService = MergeService()
        mergeService.add(User("user1", hashSetOf("mail1", "mail2")))
        assert(mergeService.getResult().size == 1)
    }

    @Test
    fun testTwoUsersOneMail() {
        mergeService = MergeService()
        mergeService.add(User("user1", hashSetOf("mail1")))
        mergeService.add(User("user2", hashSetOf("mail1")))
        assert(mergeService.getResult().size == 1)
    }

    @Test
    fun testTwoUsersTwoMails() {
        mergeService = MergeService()
        mergeService.add(User("user1", hashSetOf("mail1")))
        mergeService.add(User("user2", hashSetOf("mail2")))
        assert(mergeService.getResult().size == 2)
    }

    @Test
    fun testThreeUsersChain() {
        mergeService = MergeService()
        mergeService.add(User("user1", hashSetOf("mail1", "mail2")))
        mergeService.add(User("user2", hashSetOf("mail3", "mail4")))
        mergeService.add(User("user3", hashSetOf("mail2", "mail4")))
        assert(mergeService.getResult().size == 1)
    }

    @Test
    fun testAllMailsInResult() {
        mergeService = MergeService()
        val userCount = 100
        val mailCount = 1000
        genUsers(userCount, mailCount).forEach(mergeService::add)
        val result = mergeService.getResult()
        val resultMails = result.flatMap { user -> user.mails }.toSet()
        (1..mailCount).forEach { assert(resultMails.contains("mail$it")) }
    }

    fun genUsers(userCount : Int, mailCount : Int) : Set<User> {
        val users : Set<User> = (1..userCount).map { User("user$it", hashSetOf()) }.toSet()
        (1..mailCount).forEach {
            users.random().mails.add("mail$it")
        }
        return users
    }

}