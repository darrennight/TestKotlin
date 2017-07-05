package com.test.kotlin.patterns

/**
 * 抽象工厂模式
 * inline内联函数
 * Created by zenghao on 2017/6/5.
 */
interface Plant

class OrangePlant: Plant

class ApplePlant: Plant

abstract class PlantFactory {
    abstract fun makePlant(): Plant

    companion object {
        inline fun <reified T : Plant> createFactory(): PlantFactory = when (T::class) {
            OrangePlant::class -> OrangeFactory()
            ApplePlant::class  -> AppleFactory()
            else               -> throw IllegalArgumentException()
        }
    }
}

class AppleFactory : PlantFactory() {
    override fun makePlant(): Plant = ApplePlant()
}

class OrangeFactory : PlantFactory() {
    override fun makePlant(): Plant = OrangePlant()
}

fun main(args: Array<String>) {
    val plantFactory = PlantFactory.createFactory<OrangePlant>()
    val plant = plantFactory.makePlant()
    println("Created plant: $plant")
}