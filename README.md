CircularRevealDemo
=========================

This is a sample app that shows circular reveal animation between activity switch.

![effect](/desktop.gif)

If we jump from activity A to B, then the key steps for this effect just like below
1. Declare activity B with no animation theme.
```
<style name="NoAnimTheme" parent="AppTheme">
    <item name="android:windowAnimationStyle">@null</item>
    <item name="android:windowBackground">@android:color/transparent</item>
    <item name="android:colorBackgroundCacheHint">@null</item>
    <item name="android:windowIsTranslucent">true</item>
</style>
```

2. Send touch position to activity B when jump from A
```
val intent = Intent(this@MainActivity, AnimationActivity::class.java)
intent.putExtra("x", event.x.toInt())
intent.putExtra("y", event.y.toInt())
startActivity(intent)
```

3. Receive position in activity B and create reveal animation then start
```
private fun createRevealAnimator(reversed: Boolean, x: Int, y: Int): Animator {
    val hypot = Math.hypot(root.width.toDouble(), root.height.toDouble()).toFloat()
    val startRadius = if (reversed) hypot else 0f
    val endRadius = if (reversed) 0f else hypot

    val animator = ViewAnimationUtils.createCircularReveal(root, x, y, startRadius, endRadius)
    animator.duration = 600
    animator.interpolator = AccelerateDecelerateInterpolator()
    if (reversed) {
        animator.addListener(animatorListener)
    }
    return animator
}
```

4. If we return back from activity B to A, just use the same animation, be careful to hide content view after animation end.
```
override fun onAnimationEnd(animation: Animator?) {
    root.visibility = View.INVISIBLE
    finish()
}
```



