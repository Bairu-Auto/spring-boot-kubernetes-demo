package com.example.demo.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KubernetesTestController {


    @GetMapping("/kubernetes")
    fun getWork() = "IT WORKS"
}