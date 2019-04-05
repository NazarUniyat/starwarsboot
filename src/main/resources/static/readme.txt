Hello
Short instruction how to use this API
This service can help you when you want to calculate the body mass index of characters from Star Wars
There are 4 endpoints:
1. /calculate/{character1}/{character2} -- POST. Calculate a result of 2 characters
        character1 - name of first character
        character2 - name of second character
        request example - /calculate/Luke Skywalker/Yoda
        response example 
        {
          "status": "202 ACCEPTED",
    
          "uuid": "a1aa9d5e-c1e6-41f7-a8c3-264f6016512d"
        }
2. /getResult/{uuid} -- GET. Will return you a result of calculation that was done
        uuid - UUID, using this unique id you can get a result
        request example - /getResult/a1aa9d5e-c1e6-41f7-a8c3-264f6016512d
        {
          "firstPersonData": "Luke Skywalker has mass - 77 and height - 172 and BMI 26.027582477014604 result: little bit too much",

          "secondPersonData": "Yoda has mass - 17 and height - 66 and BMI 39.02662993572084 result: ALARM!!!! Risk of health damage",

          "bmi": "Yoda has a larger index then Luke Skywalker"

        }
3. /characters -- GET. Return a list of all characters. 3 character per page
4. /results -- GET. Return a list of all results. 3 result per page
Hope that this service will be useful for you.

If you are suddenly afraid to forget the names of the heroes, here's a list for you

