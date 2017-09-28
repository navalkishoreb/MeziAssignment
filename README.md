# MeziAssignment

FIFO ThreadPool:

Implement a Singleton FIFO ThreadPool with basic java threading constructs with following features,

- Ability to queue a task which will get executed asynchronously in a different thread
- Say we are scheduling 3 tasks (Task1, Task2, Task3). The thread pool should execute them in order meaning Task2 should executed after the Task1 is done and Task3 should get executed after Task2 is done and so on.
- Make sure that the Threadpool uses absolute minimum number of threads.
- Make sure that the ThreadPool::queueTask method is compatible with Java 8 Lambdas so that asynchronous code which needs to be executed can be put inline when calling the method. 

Flickr recent photos using FIFO Threadpool:

- Create a simple app which shows the Flicker recent photos,

https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n&nojsoncallback=1

- You may use any open source libraries for JSON parsing
- But DO NOT use any 3rd party libraries for network request and image download. 
- Use Androids built-in classes for making the network request and use the above FIFO Threadpool for performing the request in background thread
- And load the images one by one in the background thread using the above FIFO Threadpool into ImageView, do not use any 3rd party libraries here too.


<br />
<br />
<br />
<br />
<br />
<br />

- This application does not cover all network issue properly.
- This application does not manages device rotation.
- This application is not able to cancel a queued task.
- Since Task 1->Task 2->Task 3 have to be executed sequentially, it is assumed to run them over a single thread.
- This application uses GSON as json parsing library.
- This application uses LRUCache to cache images for better response.
<br />
<br />
To view logs:
adb logcat -v threadtime Mezi:v *:s

