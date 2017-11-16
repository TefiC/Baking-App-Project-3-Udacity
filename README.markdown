# Baking App

#### Project 3 | Udacity Android Developer Nanodegree

---

A Baking App that displays recipes and resources provided by [Udacity](http://udacity.com/) on [this link](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json). 

---
#### Features

---
- When the user first launches the app, available recipes will load. (If the device is a phone, they will display on a single column. Else if it's a tablet, they will display in two columns). Each recipe will display an image, its name and number of servings.

- The user can swipe to the left to access the "Favorites" tab. If the user has previously selected favorite recipes, they will appear on this tab. Else, if the user has not selected any favorite recipes, a screen with a message will be displayed.

- If the user clicks on a recipe, he/she will be taken to a details screen (if the device is a phone, a list of the recipe steps will be displayed. If it's a tablet, a details screen will be displayed with the list of steps on the left and the recipe ingredients (by default) on the right).

- If the user is on a phone and clicks on a step, he/she will be taken to an activity where he/she can navigate through the steps by swiping to change tabs or clicking on the desired tab. 

- The ingredients screen will display the recipe image and a list of the ingredients, its quantity and unit. 

- Each step will display a video using ExoPlayer or if there is no video available, an image. If none of these are available, a placeholder image will be displayed. Additionally, a description of the step will be displayed.

- The video can be toggled from full screen mode back to normal mode and vice versa.

- To select a favorite recipe, the user must click on the heart icon on the top right corner of the details screen. An empty heart means that the recipe is not selected as favorite and a full heart means that the recipe has been selected as favorite.

---

#### Widget

----

- The user can add a widget to the homescreen that will display the recipe's name, image, servings and a scrollable list of ingredients with their quantity and units.

- The user can select a recipe to display on a widget by clicking on the overflow menu at the top right corner of the screen and checking the "Add to widget" option.

- The user can toggle between an empty widget and a recipe to be displayed on the home screen widget.

- If the user selects another recipe for the widget, it will automatically be updated.

- All widgets will be updated with the same recipe data.

- The widget is resizable horizontally. 

---

#### Testing

---

- Tests are divided into Test Suites. A test suite for phones, a test suite for tablets and a test suite with general tests. 

- UI Testing uses the [Espresso](https://developer.android.com/training/testing/espresso/index.html) library .
 
---

#### Handling various scenarios

---

- If there is no internet connection, a dialog will be displayed warning the user to reconnect. If the user had loaded data previously, it the app will retain it and the user will be able to access the elements that don't require internet connection.

---

#### Atttributions

---
These were very helpful resources from which I found inspiration for elements in my code:

- [Danny roa RecyclerView matcher](http://dannyroa.com/2015/05/10/espresso-matching-views-in-recyclerview/)
- [Danny roa RecyclerView matcher code](https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java)
- [Using child fragment manager in nested fragments](https://stackoverflow.com/a/29666094)
- [Convert dp to px](https://stackoverflow.com/a/6327095)
- [Exoplayer full screen mode](https://geoffledak.com/blog/2017/09/11/how-to-add-a-fullscreen-toggle-button-to-exoplayer-in-android/)
- [Retaining fragment on rotation](https://developer.android.com/guide/topics/resources/runtime-changes.html#RetainingAnObject)
- [Creating an overflow menu](http://www.techotopia.com/index.php/Creating_and_Managing_Overflow_Menus_on_Android)
- [Placeholder image](https://pixabay.com/en/chef-cake-woman-lady-female-1773672/)
- [Chef hat image](https://pixabay.com/en/chef-cooking-hat-cap-cook-baker-295359/)
- [Girl chef image](https://pixabay.com/en/chef-cake-woman-lady-female-1773672/)
- [Change widget name](https://stackoverflow.com/questions/4536691/how-do-i-give-a-android-app-widget-a-label-name)
- [Change icon from action bar programatically](https://stackoverflow.com/questions/19882443/how-to-change-menuitem-icon-in-actionbar-programmatically)
- [Hide remote view](https://stackoverflow.com/questions/9154220/remoteviews-setviewvisibility-on-android-widget)
- [Add no connection and no favorites screen programatically](https://stackoverflow.com/questions/13889225/create-views-programmatically-using-xml-on-android)
- [Espresso intents ](https://developer.android.com/training/testing/espresso/intents.html#matching)
- [Testing intents](https://collectiveidea.com/blog/archives/2015/06/11/testing-for-android-intents-using-espresso)
- [Open overflow menu in espresso](https://stackoverflow.com/questions/33965723/espresso-click-menu-item)
- [Test suites documentation](https://github.com/junit-team/junit4/wiki/Aggregating-tests-in-suites)
- [Idling resources](https://www.youtube.com/watch?v=uCtzH0Rz5XU)
- [Espresso contrib](https://stackoverflow.com/questions/43966265/illegalaccesserror-with-countingidlingresource)
- [Espresso change timeout time](https://stackoverflow.com/questions/40245777/what-is-idlingresourcetimeoutexception-in-espresso)
- [Espresso for ImageView](https://stackoverflow.com/questions/38867613/espresso-testing-that-imageview-contains-a-drawable)
- [Espresso and ImageVIew](https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f)
- [Setting tags](https://stackoverflow.com/questions/33763425/using-espresso-to-test-drawable-changes)
- [Settings tags to Menu Items](https://stackoverflow.com/questions/15326511/how-to-set-a-tag-to-menuitem)
- [AdapterViews and Espresso](https://medium.com/google-developers/adapterviews-and-espresso-f4172aa853cf)
- [Sending extras with Espresso](http://blog.xebia.com/android-intent-extras-espresso-rules/)
- [Custom assertions with Espresso](https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso/37339656)
- [Match multiple views](https://stackoverflow.com/questions/39977902/espresso-recylerview-in-viewpager-match-multiple-views/39978656)
- [Testing espresso using Shared Preferences](https://medium.com/@SimonKaz/android-testing-setting-sharedprefs-before-launching-an-activity-558730506b7c)
- [Disable widget update](https://stackoverflow.com/questions/5641134/how-to-disable-widget-updateperiodmillis)
- [Start activity with unique extras](https://stackoverflow.com/questions/31398575/start-new-activity-from-pendingintent-with-unique-extra)
- [ActionBar style](https://stackoverflow.com/questions/30909471/tablayout-android-design-library-text-color)
- [Tabs](https://guides.codepath.com/android/Sliding-Tabs-with-PagerSlidingTabStrip)
- [Tabs FragmentPager](https://guides.codepath.com/android/ViewPager-with-FragmentPagerAdapter#layout-viewpager)
- [Full Screen video](https://geoffledak.com/blog/2017/09/11/how-to-add-a-fullscreen-toggle-button-to-exoplayer-in-android/l)
- [Full screen button](https://stackoverflow.com/questions/36990193/add-button-for-full-screen-video-with-exo-player)
- [Creating Overflow Menu](http://www.techotopia.com/index.php/Creating_and_Managing_Overflow_Menus_on_Android)

Images from Pixabay

- [Brownie](https://pixabay.com/en/brownie-dessert-cake-sweet-548591/)
- [Cheesecake](https://pixabay.com/en/food-chocolate-dessert-sweet-1283885/)
- [Yellow cake](https://pixabay.com/en/cake-golden-autumn-sweet-yellow-1002308/)
- [Nutella Pie](https://pixabay.com/en/cake-chocolate-cake-cafe-bake-2001781/)


<br>


