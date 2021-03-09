package com.tomasandfriends.bansikee.src.activities.onboarding.models


class AnswerListBody(private val reqAnswerList: List<AnswerBody>)

class AnswerBody(private val optionIdx: Int,
                 private val questionIdx: Int)