# Covid19-Analysis
The app fetches covid-19 information (confirmed recovered, deaths etc) globally and country wise and dynamically updates data daily. 
Pie chart is used to show the statistics. 
For using Retrofit library and recyclerview dependencies must implemented, put them in build.gradle(Module: app):

    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.2.0'
    implementation 'com.android.support:cardview-v7:27.1.1'
    implementation 'com.android.support:recyclerview-v7:27.1.1'

For coding pie chart :


    implementation 'com.github.PhilJay:MPAndroidChart:v3.0.3'
    repositories {
    maven { url 'https://jitpack.io' }
     }


 API Used: https://coronavirus-19-api.herokuapp.com/countries
 
 The Site used to show further details country wise : https://www.coronatracker.com/
 
 Some Screen Shot of the app:
 
 ![Screenshot_20200928-232356_Covid Analysis](https://user-images.githubusercontent.com/69322639/94466507-5a5a3f80-01e3-11eb-8117-67da2b854ef0.jpg)
 
 ![Screenshot_20200928-185944_Covid Analysis](https://user-images.githubusercontent.com/69322639/94466351-1d8e4880-01e3-11eb-95cb-f5fd4e64cfaf.jpg)
 
 ![Screenshot_20200928-232426_Covid Analysis](https://user-images.githubusercontent.com/69322639/94466358-21ba6600-01e3-11eb-842f-4a0348856e9a.jpg)
 
 ![Screenshot_20200928-232449_Covid Analysis](https://user-images.githubusercontent.com/69322639/94466365-23842980-01e3-11eb-9494-e251b412b007.jpg)

 
 
