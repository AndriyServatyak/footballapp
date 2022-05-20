# footballapp test application

## What was added as additional libraries? - Added Koin for better dependency handling

- Coil for image loading
- Logging interceptor to check logs from API and see what we receive and send.

## What was update from the initial project?

- Updated dependencies using Koin.
- Updated **NetworkResult** class to have the different types of the response: Loading, Error and  
  Success.
- Updated the **Repository** and **RemoteDataSource** for better layering of the app. In the
  future  
  we can also add the **LocalDataSource** and pass it inside **Repository** to manage data source  
  correctly.
- Fixed the UI bugs for **FixtureListFragment**.
- Moved response handling from **Fragment** to **ViewModel**, cause per MVVM rules, view should
  just  
  update UI and inform viewModel about UI actions (click, scroll, etc.)
- Updated unit tests due to arch changes.

# Short note explaining some of the following points

## What were your priorities approaching this work, and why?

First of all I tried to setup basic tools for this project and setup the MVVM approach. This
allows  
me to create clear and simple code. Next step, I updated existing code to remove useless code and  
polishing it.    
The next one, I created the new screen to see the match details. Last but not least step, I fixed  
all UI bugs for prior screen. And the last one, I updated the unit test.

## If you had an extra day, what would you have tackled next/improved?

- Here we can implement the Navigation component and update screens using the Jetpack compose (we  
  have just 2 screens, so I think it's possible).
- Also would be great to update Unit test using Koin and Coroutines. Here I preferred to write the  
  main task implementation rather than updating unit test. As I see we need to rewrite them to  
  create the reusable components, and it can takes some time.

> As optional that we can add is a linter (for instance, ktlint) to and run it before each commit.

## What would you change about the structure of the code?

Basically, code structure was totally changed here, cause I implemented MVVM arch, so lot's of
code  
was changed. Also I removed all magic numbers, and replaced them with const values.

## What would you change about the visual design of the app?

For test purposes this design is ok, but in general we can use the material design patterns and  
native UI solutions. It allows us to decrease coding time and efforts.