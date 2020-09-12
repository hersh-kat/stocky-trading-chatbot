## story_1               
* greet     
  - utter_greet         
  - utter_ask_howcanhelp
> check_asked_question


## stock_s
> check_asked_question
* stock_search{"comname":"apple"}
  - utter_lookfor
  - action_search_stock
* goodbye
  - utter_goodbye

## story_2               
> check_asked_question  
* stock_search
  - utter_ask_comname
* stock_search{"comname":"IBM"}
  - action_search_stock
* greet              
  - utter_ask_howcanhelp
* stock_search{"comname":"Apple"}
  - action_search_stock
* goodbye
  - utter_goodbye

## story_3              
> check_asked_question
* stock_search{"comname":"Google"}
  - action_search_stock
* goodbye
  - utter_goodbye

## story_4              
> check_asked_question
* stock_search{"comname":"IBM"}
  - action_search_stock
* goodbye
  - utter_goodbye

## story_5               
> check_asked_question
* stock_search
  - utter_ask_comname
* stock_search{"comname":"IBM"}
  - action_search_stock
* goodbye
  - utter_goodbye 