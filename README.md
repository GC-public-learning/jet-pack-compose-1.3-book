# jet-pack-compose-1.3-book
chapter skipped 8. Testing Android Studio Apps on a Physical Android Device

# 1) .bashrc
the PATH variable is a string that content all the paths setup for the terminal with ':' as separator for concatenation

``` bash
export ANDROID_SDK_HOME=/home/hunteroxo/.var/app/com.google.AndroidStudio/config
export ANDROID_EMULATOR_HOME=$ANDROID_SDK_HOME/.android
export ANDROID_AVD_HOME=$ANDROID_EMULATOR_HOME/avd
export ANDROID_SDK_ROOT=/home/hunteroxo/Android/Sdk

export PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools
export PATH=$PATH:ANDROID_SDK_ROOT/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_SDK_ROOT/emulator
export PATH=$PATH:/var/lib/flatpak/app/com.google.AndroidStudio/x86_64/stable/793d23300e759d2dca9cd1c8beee8c60e2c3849fe9ffb33b853d2de226c618ed/files/extra/android-studio/bin
```

apply changes -->

``` bash
source ~/.bashrc
```

# 2) studio
More memory can be alocated for playing virtual devices

# 3) preview
- possible to personalise it with gear icon on the left of the preview tag
- possible to make the preview interactive on top left of the preview

# 4) avd 
- possible to setup it in order to open it outside the aplication via the settings/tools/emulator
- emulator -list-avds : display all avds available as long as the variable "ANDROID_AVD_HOME" is setup with its path in ".bashrc"
- a virtual device can be open by the command : emulator -avd virtualDeviceName

# 5) Kotlin

## 5.0 multilines strings
without trimargin() > issue : the empty spaces before the line are taken in account as margin
``` kotlin
println("""
  line 1
  line 2
  line 3
  """	
)
```

with trimargin() >   the margin is removed if "|" is used ("|" can be replaced by another char. ex : trimargin("#")
``` kotlin
println("""
    | line 1 without margin
    line 2 with margin
    | line 3 without margin
    """.trimMargin()
)
```



## 5.1 Escapes
``` kotlin
\n 	// : new line
\r 	// : carriage return
\t 	// : Horizontal tab
\unnnn // : double byte Unicode scalar (nnnn > 4 hexadecimal digits)
```

## 5.2 var and const
``` kotlin
var : variable // ex var var1: String
val : const // ex val const1: Float
```


## 5.3 null & elvis operator
``` kotlin
val var1 : String = null // NOT ALLOWED
val var1 : String? = null // ALLOWED

val var2 : String? = null
if (var2 != null) { // if verification before the assignation > ALLOWED
	val var3: String = var2 // ALLOWED
}
val var4: String? = null
val var5 = var4?.length // executed only if var4 is not null



val var4: String? = null
val var5 = var4!!.length // ALLOWED (NOT-NULL ASSERTION > remove all nullable restrictions)

var6?.let {	// executed only if var6 is not null
	val result = firstNumber.times(it)
	print(result)
}

return var7 ?: "the var is null" // ?: = elvis operator (return a specific value if the var is null)
```

## 5.4 lateinit
``` kotlin
lateinit var var1 : String // if assignation later

if (::var1.isInitialized) { // verifiy the init of the var
print("My Name is " + myName)
}
```

## 5.5 :: (reflection)
1st : in "build.gradle" insert line :
``` groovy
implementation(kotlin("reflect"))
```
``` kotlin
::var1 // acceed properties of the object


var y = 1
fun main() {
    println("result : ${String::class.objectInstance}")

    fun isOdd(x: Int) = x % 2 != 0
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd)) // call function for each item in the list via Filter

    println(::y.get()) // the var has to be outside the function to make it working. Why ??
    println(::y.name)
    println("${::y.set(2)}, $y")
```

## 5.6 as (cast)
``` kotlin
val var1 = getResultFromFun(var2) as String // compilator know kind of the return by casting it
val var1 = getResultFromFun(var2) as? String  // return null if the cast cannot be done (safe)
```

## 5.7 operators
``` kotlin
+ - * / % // arithmetic operators
+= -= *= /= %= 	// Augmented assignment operators
x++ x-- ++x --x // incrementation
== < > <= >= != // equality operators
&& || ! // Boolean logical operators
x..y // range operator
```

## 5.8 bitwise operators
``` kotlin
var1.inv() // invert 1 and 0 of the binary value
var1.and(var2) // AND operation between all the binary numbers of the same level of 2 vars
var1.or(var2) // OR operation between all the binary numbers of the same level of 2 vars
var1.xor(var2) // XOR operation between all the binary numbers of the same level of 2 vars
var1.shl(2)	// left shift : 101 -> 10100 (the gap is 2 & the 0 replace the left empty spaces)
var1.shr(2)	// right shift : 101 -> 001 (the gap is 2 & some right values are removed)
```

## 5.9 loops

### 5.9.1 for
``` kotlin
for(index in 1..5) { // with ranges
	println("Value of index is $index")
}

for(index in "Hello") { // for strings
	println("Value of index is $index")
}

for(index in 100 downTo 90) { // backward order
	print("$index.. ")
}

for(index in 1 until 10) { // doesn't include the 10
	print("$index.. ")
}

for(index in 0 until 100 step 10) { // setup increment index
	print("$index.. ")
}
```

### other ways to use ranges
``` kotlin
(1..5).forEach {
	println(it)
}
(1..5).forEach { num -> // same forEach fun than above but with items from the list named
	println(num)
}


repeat(5) {
	println("Hello repeat")
}
```

### 5.9.2 while
``` kotlin
while(var1 < 100 ) {
	myCount++
	println(myCount)
}
```

### 5.9.3 do while
``` kotlin
do {
	i--
	println(i)
} while (i > 0)
```


### 5.9.4 statements
``` kotlin
break // end the loop
continue // redo loop from the top by bypassing the rest
```

### 5.9.5 label
specify the loop we want to break
``` kotlin
outerloop@ for(i in 1..100) {
	println("Outer loop i = $i")
	for (j in 1..100) {
		println("Inner loop j = $j")
		if (j == 10) break@outerloop
	}
}
```

## 5.10 if
``` kotlin
if(x == 10) println("x is 10")
	else if (x == 9) println("x is 9")
		else if (x == 8) println("x is 8")
			else println("x is less than 8")
}
```

## 5.11 when
``` kotlin
when (x) { // kind od switch case
	10 -> println ("x is 10")
	9 -> println("x is 9")
	8 -> println("x is 8")
	else -> println("x is less than 8")
}
```

## 5.12 functions
``` kotlin
fun multiply(x: Int, y: Int): Int {
return x * y
}

fun multiply(x: Int, y: Int): Int = x * y // same function than above but in one line
fun multiply(x: Int, y: Int) = x * y // work also without type

fun buildMessageFor(name: String = "Customer", count: Int = 0): String { // default param
return("$name, you are customer number $count")
}

fun displayStrings(vararg strings: String) { // vararg when number of params is unknown
	for (string in strings) {
		println(string)
	}
}
displayStrings("one", "two", "three", "four")
```

### 5.12.1 lambda (see book/HS/lambda)
``` kotlin
val sayHello = { println("Hello") }
sayHello()

val multiply = { val1: Int, val2: Int -> val1 * val2 } // 1. params -> return
val multiply = { val1: Int, val2: Int -> val1 *=val2 } // 2. params -> body function & no return
val multiply = { val1: Int, val2: Int -> 
	var total:Int
	total = val1 * val2
	total 
} // 3. params -> body & return (total -> end line without assigiation = return (total)
val result = multiply(10, 20) // doesn't work for 2. because no return

val myvar = ::myfunction // :: assign function ref
myvar()

val result = { val1: Int, val2: Int -> val1 * val2 }(10, 20) -// -> force the execution for 10 and 20

val result = { println("Hello"); true }() -// -> return true after executed the lambda
```

### 5.12.2 Higher-order functions
``` kotlin
fun inchesToFeet (inches: Double): Double {
	return inches * 0.0833333
}
fun inchesToYards (inches: Double): Double {
	return inches * 0.0277778
}

/* fun with a fun in param that has a double in param and a double in return + 
 * double in param -->
 */

fun outputConversion(converterFunc: (Double) -> Double, value: Double) { 
val result = converterFunc(value)
println("Result of conversion is $result")
}

outputConversion(::inchesToFeet, 22.45)
outputConversion(::inchesToYards, 22.45)

// fun with a fun in param with no arg and no return 
fun function1( funParam1 : () -> Unit ) {
	funParam()
}


// fun that return a function reference --> 
fun decideFunction(feet: Boolean): (Double) -> Double
{
	if (feet) {
		return ::inchesToFeet
	} else {
		return ::inchesToYards
	}
}

val converter = decideFunction(true)
val result = converter(22.4)
println(result)
```

## 5.13 OOP
``` kotlin
class BankAccount {
	var accountBalance: Double = 0.0
	var accountNumber: Int = 0

	fun displayBalance() {
		println("Number $accountNumber")
		println("Current balance is $accountBalance")
	}
}

val account1: BankAccount = BankAccount() // instanciation
val account1 = BankAccount() // work too
account1.displayBalance() // call of function
account1.accountNumber = 2 // call of property (possible if public)
```

### 5.13.1 constructor & init
``` kotlin
class BankAccount {
	var accountBalance: Double = 0.0
	var accountNumber: Int = 0

	constructor(number: Int, balance: Double) {
		accountNumber = number
		accountBalance = balance
	}
	constructor(number: Int) {
		accountNumber = number
		accountBalance = 10
	}
}

val account1: BankAccount = BankAccount(1,2) // instanciation const 1
val account1: BankAccount = BankAccount(1) // instanciation const 2


class BankAccount (val accountNumber: Int, var accountBalance: Double) { // 1st constructor
	var lastName: String = ""
	constructor( // 2nd constructor
		accountNumber: Int,
		accountBalance: Double,
		name: String 
	) : this(accountNumber, accountBalance) { // call of the 1st constructor from the 2nd
		lastName = name
	}
	init { // called during instanciation after constructor
		// some functions or affectations
	}
}
```

### 5.13.2 accessors (getter & setter)
``` kotlin
class BankAccount (val accountNumber: Int, var accountBalance: Double) {
	val fees: Double = 25.00

	val balanceLessFees: Double
	get() {
		return accountBalance - fees
	}
	set(value : Double) {
		accountBalance = value - fees // error should be balan
	}

	..
}

val balance1 = account1.balanceLessFees
account1.balanceLessFees = 12123.12
```

### 5.13.3 nested classes
``` kotlin
class ClassA {
	class ClassB { // classB doesn't have access to classA properties
	}
}

class ClassA {
	var myProperty: Int = 10

	inner class ClassB { // classB has access to classA properties thank to "inner"
		val result = 20 + myProperty
	}
}
```

### 5.13.4 Companion
``` kotlin
// properties and functions from the companion are reachable from all instances of the class 
// & also directly from the Class without instanciation like "Static" contents

class MyClass {
	fun showCount() {
		println("counter = " + counter)
	}
	companion object {
		var counter = 1
		fun counterUp() {
			counter += 1
		}
	}
}
fun main(args: Array<String>) {
	MyClass.counterUp()
	println(MyClass.counter)
}
```

### 5.13.5 inheritance
``` kotlin
open class MyParentClass { // "open" mandatory to have children
	var myProperty: Int = 0
}
class MySubClass : MyParentClass() {
}

open class MyParentClass {
	var myProperty: Int = 0
	constructor(number: Int) {
		myProperty = number
	}

	open fun displayBalance()
	{
		println("Number $accountNumber")
		println("Current balance is $accountBalance")
	}
}
class MySubClass : MyParentClass {
	constructor(number: Int) : super(number) {
		// additional code
	}
	override fun displayBalance()
	{
		super.displayBalance() // if some code from the root function needs to be reused
		println("Prevailing interest rate is $interestRate")
	}
}
```

# 6) Compose

## 6.1 layout, foundation & Material design components

### layout
- Box
- BoxWithConstraints
- Column
- ConstraintLayout
- Row

### Foundation
- BaseTextField
- Canvas
- Image
- LazyColumn
- LazyRow
- Shape
- Text

### Material design
- AlertDialog
- Button
- Card
- CircularProgressIndicator
- DropdownMenu
- Checkbox
- FloatingActionButton
- LinearProgressIndicator
- ModalDrawer
- RadioButton
- Scaffold
- Slider
- Snackbar
- Switch
- TextField
- TopAppBar
- BottomNavigation

## 6.2 Stateful vs Stateless (see book/mMyApplication3states for more details)
``` kotlin
@Composable
fun DemoScreen() { // stateful
	var sliderPosition by remember { mutableStateOf(20f) }
	.
	.
}
@Composable
fun DemoSlider(sliderPosition: Float, onPositionChange : (Float) -> Unit ) { // stateless
	Slider(
		modifier = Modifier.padding(10.dp),
		valueRange = 20f..40f,
		value = sliderPosition,
		onValueChange = onPositionChange
	)
}
```

## 6.3 state (see book/mMyApplication3states for more details))

### STATE HOISTING

goal : make the child composable stateless in order to make it easier to reuse

so an ancestor or the parent manage the state that can be passed to other child functions

if no state hoisting like that : 

NOT TO DO :
``` kotlin
@Composable
fun MyTextField() {
	var textState by remember { mutableStateOf("") } // state
	val onTextChange = { text : String -> textState = text } // event handler

	TextField(
		value = textState,
		onValueChange = onTextChange
	)
}

/* issues :
 * 1. the textState cannot be passed to other parent or same level functions
 * 2. another state canot be passed to the function
```

TO DO :
``` kotlin
 @Composable 
 fun HomeScreen() {
 	var textState by remember { mutableStateOf("") } // state
 	val onTextChange = { text : String -> textState = text } // event Handler
 	MyTextField(text = textState, onTextChange = onTextChange)
 }
@Composable // reusable component : any state or eventHandler can be passed to it
// and text can be passed to other components
fun MyTextField( 
	text: String,
	onTextChange: (String) -> Unit
	) {
	TextField(
		value = textState,
		onValueChange = onTextChange
	)
}
``` 

### rememberSaveable
``` kotlin
var textState by rememberSaveable { mutableStateOf("")
``` 

during some changes the activity is rebuilt and by the way reinit the states with their initial values
"rememberSaveable" has to be used to keep the states even after activity rebuilt

### Composition Local (see book/yapplication4complocal)
goal : no need to pass as arg the states in the hierarchy (from children to children)
the State declared in this way is local to the branch of the hierarchy tree in which a value is assigned

exemple : 
``` kotlin
 val LocalColor = staticCompositionLocalOf { Color.Gray } // needs to be outside all funs or classes
 // the Color object is the state

@androidx.compose.runtime.Composable
fun Comp1 () {
    // no ternary operator in kotlin but if else can be used like that ->
    val color = if(isSystemInDarkTheme()) Color.Blue else Color(0xFFffdbcf)
    Column {
        Comp2()
        CompositionLocalProvider(LocalColor provides color) { 
        // provide the state redefined to the Comp3() child
            Comp3()
        }
    }
}

@androidx.compose.runtime.Composable
fun Comp3 () {
    Text(
        text = "Comp 3",
        modifier = Modifier.background(LocalColor.current) // use the last affected color
    )
    CompositionLocalProvider(LocalColor provides Color.Red) { 
    	// the Comp5() is provided the state but with a different color setup
    	// so only this child and its child will use this color, the ancestors will use the former setup
        Comp5()
    }
}
```

## 6.4 Slot API (Composable as param) (see book/MyApplication5slots)
``` kotlin
@Composable
fun SlotDemo(
	topContent: @Composable () -> Unit,
	middleContent: @Composable () -> Unit
) { // composable passed a param
	Column {
		Text("Top Text")
		middleContent() // slot > call of the composable
		Text("Bottom Text")
	}
}

SlotDemo(topContent = { Comp1 () }, middleContent = { Comp2 () }) // or ->
SlotDemo (
	{ Comp1 () },
	{ Comp2 () }
)
```

## 6.5 Modifiers (see book/MyApplication6modifiers)
Common built-in modifiers list

"https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier"
|Modifer	| Explanations |
| ------ 	| ------ |
| .background 	| Draws a solid colored shape behind the composable |
| .clickable 	| Specifies a handler to be called when the composable is clicked. Also causes a ripple effect when the click is performed |
| .clip 	| Clips the composable content to a specified shape |
| .fillMaxHeight | The composable will be sized to fit the maximum height permitted by its parent |
| .fillMaxSize 	| The composable will be sized to fit the maximum height and width permitted by its parent |
| .fillMaxWidth | The composable will be sized to fit the maximum width permitted by its parent |
| .layout 	| Used when implementing custom layout behavior, a topic covered in the chapter entitled “Building Custom Layouts” |
| .offset 	| Positions the composable the specified distance from its current position along the x and y-axis |
| .padding 	| Adds space around a composable. Parameters can be used to apply spacing to all four sides or to specify different padding for each side |
| .rotate 	| Rotates the composable on its center point by a specified number of degrees |
| .scale 	| Increase or reduce the size of the composable by the specified scale factor |
| .scrollable 	| Enables scrolling for a composable that extends beyond the viewable area of the layout in which it is contained |
| .size 	| Used to specify the height and width of a composable. In the absence of a size setting, the composable will be sized to accommodate its content (referred to as wrapping) |

modifiers combining
``` kotlin
val combinedModifier = firstModifier.then(secondModifier).then(thirdModifier) ...

val modifier2 = Modifier.width(150.dp)
CustomImage(
    image = R.drawable.vacation,
    Modifier
        .then(other = modifier2)
        // the "Width" param from modifier2 is the only one taken because it is the 1st
        .padding(16.dp)
        .width(270.dp)
        .clip(shape = RoundedCornerShape(30.dp))
        .width(10.dp) // if 2 or more "width" params -> only the 1st declared is taken
)
```

## 6.6 Annotated strings (see book/MyApplication7annotatedstrings)

### 6.6.1 SpanStyle
``` kotlin
buildAnnotatedString {
	withStyle(style = SpanStyle( /* style settings */)) {
		append(/* text string */)
	}
	withStyle(style = SpanStyle(/* style settings */)) {
		append(/* more text */)
	}
.
.
}
```

#### SpanStyle options
- color
- fontSize
- fontWeight
- fontStyle
- fontSynthesis
- fontFamily
- fontFeatureSettings
- letterSpacing
- baselineShift,
- textGeometricTransform
- localeList
- background
- textDecoration
- shadow

### 6.6.2 ParaphStyle
``` kotlin
buildAnnotatedString {
	withStyle(style = ParagraphStyle( /* style settings */)) {
		append(/* text string */)
	}
	withStyle(style = ParagraphStyle(/* style settings */))
		append(/* more text */)
	}
.
.
}
```


#### ParaphStyle options
ParagraphStyle, on the other hand, applies a style to paragraphs and can be used to modify the following properties:
- textAlign
- textDirection
- lineHeight
- textIndent

``` kotlin
val myColors = listOf( /* color list */)
Text(
	text = "text here",
	style = TextStyle( // can be SpanStyle too
		brush = Brush.radialGradient( // brush
			colors = myColors
		)
	)
)
```

## 6.7 Rows & columns (see book/MyApplication8rowcolumn)

### Alignment
``` kotlin
Row(verticalAlignment = Alignment.CenterVertically){}
```

### Alignment Params

#### ROW
| Alignment		| Explanations |
|----------		|--------------|
| .Top 			| Aligns the content at the top of the "ROW" content area |
| .CenterVertically 	| Positions the content in the vertical center of the "ROW" content area |
| .Bottom 		| Aligns the content at the bottom of the "ROW" content area |

When working with the Column composable, the horizontalAlignment parameter is used to configure alignment
along the horizontal axis. Acceptable values are as follows:

#### COLUMN
| Alignment		| Explanations |
|-----------		|--------------|
| .Start 		| Aligns the content at the horizontal start of the "COLUMN" content area |
| .CenterHorizontally 	| Positions the content in the horizontal center of the "COLUMN" content area |
| .End 			| Aligns the content at the horizontal end of the "COLUMN" content area |

### Arrangement
``` kotlin
Column(verticalArrangement = Arrangement.Center){}
```
#### Arrangements params

##### ROW
| Arrangement	| Explanations |
|-------------	|--------------|
| .Start 	| Aligns the content at the horizontal start of the "ROW" content area |
| .Center 	| Positions the content in the horizontal center of the "ROW" content area |
| .End 		| Aligns the content at the horizontal end of the "ROW" content area |

##### COLUMN
| Arrangement	| Explanations |
|-------------	|--------------|
| .Top 		| Aligns the content at the top of the "COLUMN" content area |
| .Center 	| Positions the content in the vertical center of the "COLUMN" content area |
| .Bottom 	| Aligns the content at the bottom of the "COLUMN" content area |

#### ARRANGEMENT SPACING
``` kotlin
Row(horizontalArrangement = Arrangement.SpaceEvenly
```
| Arrangement	| Explanations |
|-------------	|--------------|
| .SpaceEvenly 	| Children are spaced equally, including space before the first and after the last child |
| .SpaceBetween | Children are spaced equally, with no space allocation before the first and after the last child |
| .SpaceAround 	| Children are spaced equally, including half spacing before the first and after the last child |

### Row and Column scope
``` kotlin
Row(modifier = Modifier.height(300.dp)) {
	TextCell("1", Modifier.align(Alignment.Top))
	TextCell("2", Modifier.align(Alignment.CenterVertically))
	TextCell("3", Modifier.align(Alignment.Bottom))
}
```
| Modifier| Explanations |
|-------------	|--------------|
| .align() 		| Allows the child to be aligned horizontally using Alignment.CenterHorizontally, Alignment. Start, and Alignment.End values |
| .alignBy()		| Aligns a child horizontally with other siblings on which the alignBy() modifier has also been applied |
| .align() 		| Allows the child to be aligned vertically using Alignment.CenterVertically, Alignment.Top, and Alignment.Bottom values |
| .alignBy()		| Aligns a child with other siblings on which the alignBy() modifier has also been applied. Alignment may be performed by baseline or using custom alignment line configurations |
| .alignByBaseline() 	| Aligns the baseline of a child with any siblings that have also been configured |
| .paddingFrom() 	| Allows padding to be added to the alignment line of a child |
| .weight() 		| Sets the width of the child relative to the weight values assigned to its siblings. > 0.2f, 0.8f |


## 6.8 Box (see book/MyApplication9box)
 with box, all chids are in a stack and all displayed by default from the top left. the last item is on the top
 
### Box alignment
- TopStart
- TopCenter
- TopEnd
- CenterStart
- Center
- CenterEnd
- BottomCenter
- BottomEnd
- BottomStart

### BoxScope
-  align() : Aligns the child within the Box content area using the specified Alignment value.
- matchParentSize() : Sizes the child on which the modifier is applied to match the size of the parent Box.

### Modifier.clip
- clip(CircleShape)
- clip(RoundedCornerShape(30.dp))
- clip(CutCornerShape(30.dp))


## 6.9 Layout modifier (see book/book/MyApplication10LayoutModifier
``` kotlin
// declaration
fun Modifier.exampleLayout(fraction: Float, increaseY : (Int) -> Int = {value : Int -> 0})
        = layout { measurable, constraints ->
        	// measurable = child
        	// constraints = max and min width and height
        val placeable = measurable.measure(constraints) // child with constraints
        val x = (placeable.width * fraction).roundToInt()


        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = x, y = increaseY(placeable.height))
        }
    }

// call
Box(modifier = Modifier.size(120.dp, 80.dp)) {
        var y = 0
        val increasY = { value : Int -> y += value; y }
        ColorBox(
            Modifier.exampleLayout(0f).background(Color.Blue)
        )
        ColorBox(
            Modifier.exampleLayout(0.25f, increasY).background(Color.Green)
        )
}
```

## 6.10 custom layout (see book/MyApplication11CustomLayout)
``` kotlin
@Composable
fun DoNothingLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables : List<Measurable>, constraints : Constraints ->
//        val placeables = mutableListOf<Placeable>()
//        measurables.forEach{
//            placeables.add(it.measure(constraints))
//        }
        // best way > no need to crate a mutableListOf
        val placeables = measurables.map { measurable ->
            // measure each child
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = 0)
            }
        }
    }
}
// call
DoNothingLayout(Modifier.padding(8.dp)) {
    Text(text = "line 1")MyApplication12constraintlayout
    Text(text = "line 2")
    Text(text = "line 3")
    Text(text = "line 4")
}
```

## 6.11 Constraint layout (see book/MyApplication12constraintlayout)

### 6.11.1 Constraints

### 6.11.2 Margins
is the last constriant setup and has highest priority
``` kotlin
MyButton(
    text = "Button 1",
    Modifier.constrainAs(button1){
        top.linkTo(parent.top, margin = 20.dp)
})
```


### 6.11.3 Opposing Constraints
spread the free space side sizes equaly between oposites constraints
``` kotlin
start.linkTo(parent.start, margin = 30.dp) // can be mixed with margin
end.linkTo(parent.end)
```


### 6.11.4 Constraint Bias
spread the free space side size following a percentage

used for responsive designs instead of margins
``` kotlin
linkTo(parent.top, parent.bottom, bias = 0.9f) // bias assigned to the left/top spaces and rest to the right/bottom
```

### 6.11.5 Chains
vertical or horizontal group of objects

#### Chain Styles 
- spread Chain : spread the space equaly between the elements
- Spread inside Chain : spread the space equaly between the elements and no space on the sides
- Weighted Chain : width or eight of each element based to one element proportion
- packed Chains : elements are packed without any space

``` kotlin
Box(Modifier.size(width = 200.dp, height = 200.dp)) {
	ConstraintLayout(
	    Modifier
	        .size(width = 200.dp, height = 200.dp)
	        .background(Color.Cyan)
	){
	    val (button1, button2, button3) = createRefs()
	    createVerticalChain(
	        button1, button2, button3,
	//                    chainStyle = ChainStyle.SpreadInside
	        chainStyle = ChainStyle.Packed
	        ) // chain
	    MyButton(
	        text = "Button 1",
	        Modifier.constrainAs(button1) {
	            centerHorizontallyTo(parent)
	        }
	    )
	    MyButton(
	        text = "Button 2",
	        Modifier.constrainAs(button2) {
	            centerHorizontallyTo(parent)
	        }
	    )
	    MyButton( // all items for the chain have to be implemented to make the chain working
	        text = "Button 3",10.dp)
                        width = Dimension.fillToConstr
	        Modifier.constrainAs(button3) {
	            centerHorizontallyTo(parent)
	        }
	    )
	}
}
````
### 6.11.6 Guidelines
invisible element with which the constraints can be setup

``` kotlin
// assigment
val guide = createGuidelineFromTop(offset = 60.dp) // horizontal guideline (measure)
val guide = createGuidelineFromStart(fraction = .10f) // vertical guideline (percent)

 // call
 Modifier.constrainAs(button1) {
    start.linkTo(guide) // use of guideline
}
```

### 6.11.7 Barriers & Dimensions
impede some elements to overlap on other ones when they are modified
``` kotlin
// assignment
val barrier = createEndBarrier(button1, button2) // vertical barrier

// call
Modifier.constrainAs(button3) {
    start.linkTo(barrier, margin = 10.dp) // use of barrier

    width = Dimension.fillToConstraints // dimension
    height = Dimension.fillToConstraints // dimension
}
```

| Dimension			| Explanations |
|-----------			|--------------|
| .preferredWrapContent 	| The size of the composable is dictated by the content it contains (i.e. text or graphics) subject to prevailing constraints |
| .wrapContent 			| The size of the composable is dictated by the content it contains regardless of revailing constraints |
| .fillToConstraints 		| Allows the composable to be sized to fill the space allowed by the prevailing constraints |
| .preferredValue 		| The composable is fixed to specified dimensions subject to the prevailing constraints. |
| .value			| The composable is fixed to specified dimensions regardless of the prevailing constraints |

### 6.11.8 Constraint set
``` kotlin
// declaration
private fun myConstraintSet(margin: Dp) : ConstraintSet
    = ConstraintSet {
        val button4 = createRefFor("button4")

        constrain(button4) {
            linkTo(
                parent.top, parent.bottom,
                topMargin = margin, bottomMargin = margin
            )
            height = Dimension.fillToConstraints
    	}
}

// call
Box(Modifier.size(width = 200.dp, height = 200.dp)) {
	// ! createRefs() cannot be used if a constraintSet is used
    val constraints = myConstraintSet(margin = 30.dp) // custom constraints
    ConstraintLayout(
        constraints,
        Modifier
            .size(width = 200.dp, height = 200.dp)
            .background(Color.Yellow)
    ) {
        MyButton(text = "Button 4", Modifier.layoutId("button4"))
    }


}
```

## 6.12 Intrinsic size (see book/MyApplication13intrinsicsize)
The parent is allowed to measure its children and makes its size in function
``` kotlin
Column(
    Modifier
    .width(200.dp)
    .padding(5.dp)) {
//  Column(Modifier.width(IntrinsicSize.Max)) {
	Column(Modifier.width(IntrinsicSize.Min)) { // use of intrinsic measurment
	    // .Min > the biggest word of the textField defines the column width as long as all column children are smaller
        // .Max > the textField content defines the column width as long as all column children are smaller
	    Text(
	        text = textState,
	        modifier = Modifier.padding(start = 4.dp)
	    )
	    Box(
	        Modifier
	            .height(20.dp)
	            .fillMaxWidth() // as it takes the column width we can see the columne size in blue
	            .background(Color.Blue)
	    )
	    Box(Modifier
	        .height(20.dp)
	        .width(100.dp)
	        .background(Color.Red))
	}
	MyTextField(text = textState, onTextChange = onTextChange)
}
```

## 6.13 Coroutines (assynchronous programming) (see book/MyApplication14coroutines)

### 6.13.1 built-in scopes
specific group of routines in which each coroutine can be setup

- GlobalScope : GlobalScope is used to launch top-level coroutines which are tied to the entire lifecycle of the
application. Since this has the potential for coroutines in this scope to continue running when not needed
(for example when an Activity exits) use of this scope is not recommended for use in Android applications.
Coroutines running in GlobalScope are considered to be using unstructured concurrency.

- ViewModelScope : Provided specifically for use in ViewModel instances when using the Jetpack architecture
ViewModel component. Coroutines launched in this scope from within a ViewModel instance are automatically
canceled by the Kotlin runtime system when the corresponding ViewModel instance is destroyed.

- LifecycleScope : Every lifecycle owner has associated with it a LifecycleScope. This scope is canceled when
the corresponding lifecycle owner is destroyed making it particularly useful for launching coroutines from
within composables and activities.

``` kotlin
val coroutineScope = rememberCoroutineScope() // declare the coroutine scope

coroutineScope.cancel() // cancel all coroutines from the scope
```

### 6.13.2 Suspend functions
contains code of coroutine
``` kotlin
suspend fun mySlowTask() { 
	// Perform long-running task here
}
```

### 6.13.3 Coroutine dispatchers
Kotlin maintains threads for different types of asynchronous activity and, when launching a coroutine, you have
the option to specify a specific dispatcher from the following options: 

| Dispatcher		| Explanations |
|------------		|--------------|
| .Main 		| Runs the coroutine on the main thread and is suitable for coroutines that need to make changes to the UI and as a general-purpose option for performing lightweight tasks. |
| .IO			| Recommended for coroutines that perform network, disk, or database operations |
| .Default 	| Intended for CPU-intensive tasks such as sorting data or performing complex calculations |

``` kotlin
coroutineScope.launch(Dispatchers.IO) {
	performSlowTask()
}
```

### 6.13.3 Coroutine builders
| Builder	| Explanations |
|---------	|--------------|
| .launch 	| Starts a coroutine without blocking the current thread and does not return a result to the caller. Use this builder when calling a suspend function from within a traditional function, and when the results of the coroutine do not need to be handled (sometimes referred to as “fire and forget” coroutines) |
| .async 	| Starts a coroutine and allows the caller to wait for a result using the await() function without blocking the current thread. Use async when you have multiple coroutines that need to run in parallel. The async builder can only be used from within another suspend function |
| .withContext 	| This allows a coroutine to be launched in a different context from that used by the parent coroutine. A coroutine running using the Main context could, for example, launch a child coroutine in the default context using this builder. The withContext builder also provides a useful alternative to async when returning results from a coroutine. |
| .coroutineScope | The coroutineScope builder is ideal for situations where a suspend function launches multiple coroutines that will run in parallel and where some action needs to take place only when all the coroutines reach completion. If those coroutines are launched using the coroutineScope builder, the calling function will not return until all child coroutines have completed. When using coroutineScope, a failure in any of the coroutines will result in the cancellation of all other coroutines |
| .supervisorScope | Similar to the coroutineScope outlined above, with the exception that a failure in one child does not result in cancellation of the other coroutines |
| runBlocking | Starts a coroutine and blocks the current thread until the coroutine reaches completion. This is typically the opposite of what is wanted from coroutines but is useful for testing code and integrating legacy code and libraries otherwise to be avoided |

### 6.13.4 jobs
instances returned by the builders

##### job properties
isActive, isCompleted, and isCancelled

##### job methods
cancell(), cancellChildren(), join(), cancelAndJoin()

exemples
``` kotlin
@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    var counter by remember { mutableStateOf(0) }
    val increaseCounter = { counter+=1 }

    Column() {
        // the button continue to increase the text even if performSlowtask is running (see LogCat)
        MyButton(increaseCounter, coroutineScope)
        MyText(counter = counter)
    }
}

@Composable
fun MyButton(increaseCounter: ()-> Unit, coroutineScope: CoroutineScope) {
    Button(onClick = {
        coroutineScope.launch { performSlowTask() }
        increaseCounter()
    }) {
        Text(text = "Click me")
    }
}

@Composable
fun MyText(counter: Int){
    Text(text = counter.toString())
}

suspend fun performSlowTask() {
    println("performSlowTask before") // see in Logcat > filter: System.out
    delay(5000) // simulates long-running task
    println("performSlowTask after")
}
```

### 6.13.5 Chanels
Send data from one couroutine to another one

``` kotlin
suspend fun performTask1(channel: Channel<Int>) {
    (1..6).forEach {
        channel.send(it) // "it" is sent = nums from 1 to 6
    }
}
suspend fun performTask2(channel: Channel<Int>) {
    repeat(6) {
        println("Received: ${ channel.receive() }") // the nums are received
    }
}

@Composable
fun MyButton2(coroutineScope: CoroutineScope, channel: Channel<Int>) {
    Button(onClick = {
        coroutineScope.launch() {
            coroutineScope.launch(Dispatchers.Main) { performTask1(channel) }
            coroutineScope.launch(Dispatchers.Main) { performTask2(channel) }
        }

    }) {
        Text(text = "Click me too")
    }
}


fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    val channel = Channel<Int>()

    Column() {
        MyButton2(coroutineScope = coroutineScope, channel = channel)
    }
}
```


### 6.13.6 side effects
- SideEffect : executed and relaunch on every recomposotion of the parent composable
- LaunchedEffect(key1, key2) : works with keys (params) > same like sideEffect but if a param is modified, the current  coroutine is cancelled and a new one is launched

``` kotlin
// declaration of suspend function (wathever the code inside)
suspend fun performSlowTask2() {
    println("performSlowTask 2 before") // see in Logcat > filter: System.out
    delay(5000) // simulates long-running task
    println("performSlowTask 2 after")
}

suspend fun performSlowTask2() {
    println("performSlowTask 3 before") // see in Logcat > filter: System.out
    delay(5000) // simulates long-running task
    println("performSlowTask 3 after")
}

// call

fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {  // if Unit passed (void instance) > coroutine doesn't need to be recreated through recompositions
        coroutineScope.launch { performSlowTask2() }
    }
    SideEffect { // always no params, executed and relaunch on every recomposotion of the parent composable
        coroutineScope.launch { performSlowTask3() }
    }
}
```
## 6.14 Scrollable row & column lists (see book/MyApplication15rowandcolumnlists)

``` kotlin
@Composable
fun ColumnList() {
    val scrollState = rememberScrollState() // scrollState
    val coroutineScope = rememberCoroutineScope() // to setup special options for the scroll list

    Column {
        Row {
            Button(
                onClick = { coroutineScope.launch { scrollState.animateScrollTo(0) } },
                modifier = Modifier
//                    .fillMaxWidth(0.5f)
                    .weight(0.5f)
                    // other way to use the available space > each brother's size is setup in function of the other ones
                    .padding(2.dp)
            ) {
                Text(text = "Top")
            }
            Button(
                onClick = { coroutineScope.launch { scrollState.animateScrollTo(scrollState.maxValue) } },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(2.dp)
            ) {
                Text(text = "End")
            }
        }
        Column(Modifier.verticalScroll(scrollState)) { // use of scrollState
            repeat(500) {
                Text(
                    text = "Item $it",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun RowList() {
    val scrollState = rememberScrollState()
    Row(
        Modifier.horizontalScroll(scrollState)
    ) {
        repeat(50) {
            Text(
                text = "$it",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(5.dp)
            )

        }
    }
}
```

## 6.15 Lazy lists (see book/MyApplication16lazylists)

bonus : image loading library : https://coil-kt.github.io/coil/

build.gradle setup (for the bonus)
``` groovy
compileSdk = 34
implementation("io.coil-kt:coil:2.4.0")
```

AndroidManifest setup : adding permissions for internet (for the bonus)
``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

 <uses-permission android:name="android.permission.INTERNET" />
```
car_list.xml file content > file copied in res/values/ project folder
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="car_array">
        <item>Buick Century</item>
        <item>Buick LaSabre</item>
        <item>Buick Roadmaster</item>
        <item>Buick Special Riviera</item>
        <item>Cadillac Couple De Ville</item>
        <item>Cadillac Eldorado</item>
        <item>Cadillac Fleetwood</item>
        <item>Cadillac Series 62</item>
        <item>Cadillac Seville</item>
        <item>Ford Fairlane</item>
        <item>Ford Galaxie 500</item>
        <item>Ford Mustang</item>
        <item>Ford Thunderbird</item>
        <item>GMC Le Mans</item>
        <item>Plymouth Fury</item>
        <item>Plymouth GTX</item>
        <item>Plymouth Roadrunner</item>
    </string-array>
</resources>
```

Only lazy list code, other functions has been created in order to displays the downloaded images wrapped in cards with some info > see the App 16

the xml file is converted to a "Array<String>?" on the beggining of the "onCreate" function
``` kotlin
itemArray = resources.getStringArray(R.array.car_array) // call of the array string xml
```
then used as param of the Mainscreen function and casted as Array<out String>
``` kotlin
MainScreen(itemArray = itemArray as Array<out String>)
```

``` kotlin
@Composable
fun MainScreen(itemArray: Array<out String>) {
    val context = LocalContext.current // in order to generate a toast in the good screen

    val onListItemClick = { text : String ->
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
    LazyColumn{
        items(itemArray) {model ->
            MyListItem(
                item = model,
                onItemClick = onListItemClick
                )
        }
    }
}
```