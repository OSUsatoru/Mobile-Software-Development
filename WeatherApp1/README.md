# Instruction

This screen capture depicts roughly what the app should look like (though you don't have to exactly match the styling here) and how it should behave:

![Screen capture of working app](screencap.gif)

## Write a basic app to display dummy weather data

An initial template for an Android app, currently, contains one extremely basic "hello world" activity.  Your job is to modify this activity to create the beginnings of a basic weather app.  Here's what you need to do to accomplish this:

  * Create a custom Kotlin class to represent the weather forecast for a specific day or other period of time.  This class should have properties to represent the following forecast data (at a minimum; feel free to include more forecast data if you want):
    * The date/time associated with the forecast being represented.
    * The high and low temperatures for the forecast period.
    * The probability of precipitation associated with the forecast period (e.g. 75%).
    * A short description of the weather associated with the forecast period (e.g. "Sunny and warm").
    * A longer version of the weather description (e.g. "Cloudless and generally warm, with a high of 75F and a low of 57F.  Some high clouds late in the day.").

  * Allocate a list of 10-15 objects of the custom class you just created, and initialize it with some hard-coded, dummy weather data.

  * Use the `RecyclerView` framework to display the entire array of forecast data as a scrollable vertical list of cards.  To accomplish this, you'll need to do the following:
    * Add a `RecyclerView` widget to the main activity's layout.
    * Create a new layout defining a card that depicts a single forecast element in your array.  This card should display all of the information contained in the forecast object *except the long version of the forecast*, which we'll use later.  Try to make your card look as good as you can.  For example, add margins, borders, and padding as appropriate to create space inside and around the card and its elements, and try to organize the information displayed in each card in an intuitive and visually appealing way.  If you like, try to use some of the principles from the following articles when you're styling your card:
      * [Android styling: themes vs styles](https://medium.com/androiddevelopers/android-styling-themes-vs-styles-ebe05f917578)
      * [Android styling: common theme attributes](https://medium.com/androiddevelopers/android-styling-common-theme-attributes-8f7c50c9eaba)
    * Write a class that extends `RecyclerView.Adapter`.  Within this class, you will:
      * Implement an inner class that extends `RecyclerView.ViewHolder`.  Objects of this class will represent individual items in your forecast list.
      * Write methods to bind weather forecast data from your array to your view holder objects.  These methods will be used to make sure the list stays up to date as the user scrolls.
    * Within your main activity's `onCreate()` method, create a new layout manager and connect it to the `RecyclerView`.  Then, create a new object of your adapter class and attach that to the `RecyclerView`.  Make sure to store your dummy weather forecast data in the adapter.

  Importantly, the app you write should not have any extraneous components.  For example, you should not include a text entry box and/or a button like we did in the app we wrote in lecture, since the user will not have to provide any input for this app.

## Handle user clicks on items in the weather list

Finally, you should add functionality that handles clicks on weather forecast elements in your `RecyclerView` list.  Specifically, modify your main activity and/or adapter class so that, when the user clicks on one of the weather forecast elements in your list, a [`Snackbar`](https://developer.android.com/training/snackbar) is displayed that contains the longer version of the forecast description corresponding to the list item that was clicked.  Note that to do this, you'll have to listen for clicks on the `RecyclerView` view holders themselves (specifically to the `View` associated with the view holder).

