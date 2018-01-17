# kotlinexample
kotlin example project.

###### 大纲
```
- 基础语法和数据类型
- 面向对象  
    <small><i>_继承，接口，扩展_</i></small>
- 数据类和密封类，泛型和枚举
- 对象表达式和声明
- 设计模式
```


###### 为什么选择Kotlin？
```
简洁：大大减少代码数量
安全：避免空指针异常
互操作性：完全兼容Java
工具友好：任何Java IDE或者命令行构建
```

###### Tips 
```aidl

```

###### 关键字
```
声明：
	open: Kotlin 默认会为每个变量和方法添加 final 修饰符，目的是为了程序运行的性能
	internal：在同一个模块中可见（Module）
	data：数据类，默认实现setter和getter
	companion object: 伴生对象
		实例:获取全局的Application实例
		companion object {
        	lateinit var instance: App
    	}
	lateinit（只能修饰 var ） 和 by lazy (只能修饰 val ): 都实现懒加载
	var和val: 可变变量和不可变变量
判断：
	when(switch) in is
运算：
	and or
类型
	Any(object) Unit(void)
基本类型
	Boolean Byte Short Int Long Float Double Char
其他
	by: 直接支持委托模式，更加优雅，简洁。Kotlin 通过关键字 by 实现委托。委托模式是软件设计模式中的一项基本技巧。在委托模式中，有两个对象参与处理同一个请求，接受请求的对象将请求委托给另一个对象来处理。
	as lazy init downTo
	object: 用于创建单例模式
	let: 默认当前这个对象作为闭包的it参数，返回值是函数里面最后一行，或者指定return
		fun testLet(): Int {
    		// fun <T, R> T.let(f: (T) -> R): R { f(this)}
		    "testLet".let {
		        println(it)
		        println(it)
		        println(it)
		        return 1
		    }
		}
		//运行结果
		//testLet
		//testLet
		//testLet
	apply: 调用某对象的apply函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象
		fun testApply() {
		    // fun <T> T.apply(f: T.() -> Unit): T { f(); return this }
		    ArrayList<String>().apply {
		        add("testApply")
		        add("testApply")
		        add("testApply")
		        println("this = " + this)
		    }.let { println(it) }
		}

		// 运行结果
		// this = [testApply, testApply, testApply]
		// [testApply, testApply, testApply]
	with: 函数是一个单独的函数，并不是Kotlin中的extension，所以调用方式有点不一样，返回是最后一行，然后可以直接调用对象的方法，感觉像是let和apply的结合。
		fun testWith() {
		    // fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()
		    with(ArrayList<String>()) {
		        add("testWith")
		        add("testWith")
		        add("testWith")
		        println("this = " + this)
		    }.let { println(it) }
		}
		// 运行结果
		// this = [testWith, testWith, testWith]
		// kotlin.Unit
	abstract: 抽象类 一个类或一些成员可能被声明成 abstract,不用给一个抽象类或函数添加 open 注解，它默认是带着的。
	sealed: 密封类用于代表严格的类结构，值只能是有限集合中的某中类型，不可以是任何其它类型。这就相当于一个枚举类的扩展：枚举值集合的类型是严格限制的，但每个枚举常量只有一个实例，而密封类的子类可以有包含不同状态的多个实例。 
	声明密封类需要在 class 前加一个 sealed 修饰符。密封类可以有子类但必须全部嵌套在密封类声明内部.
```
###### NULL检查机制
```
//类型后面加?表示可为空
var age: String? = "23" 
//抛出空指针异常
val ages = age!!.toInt()
//不做处理返回 null
val ages1 = age?.toInt()
//age为空返回-1
val ages2 = age?.toInt() ?: -1
```
###### 类的修饰符
```
classModifier: 类属性修饰符，标示类本身特性。
    abstract    // 抽象类  
    final       // 类不可继承，默认属性
    enum        // 枚举类
    open        // 类可继承，类默认是final的
    annotation  // 注解类
accessModifier: 访问权限修饰符
    private    // 仅在同一个文件中可见
    protected  // 同一个文件中或子类可见
    public     // 所有调用的地方都可见
    internal   // 同一个模块中可见
```