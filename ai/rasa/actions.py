from rasa_core.actions import Action
from rasa_core.events import SlotSet
import subprocess

class ActionStockPriceSearch(Action):
    def name(self):
        return 'action_search_stock'

    def run(self, dispatcher, tracker, domain):
        comname = str(tracker.get_slot("comname"))
        data = str(subprocess.check_output(['java','-jar','data.jar',comname]))
        price = data[data.index("Price: ")+6:data.index(", Prev close")]
        dispatcher.utter_message("The price of " + comname + " is " + price)
        return []


class ActionSuggest(Action):
    def name(self):
        return 'action_suggest'

    def run(self, dispatcher, tracker, domain):
        dispatcher.utter_message("papi's pizza place")
        return []
