Feature: Draw Card

 Scenario: Game created
  Given My game is created
  Then Player 1 draw 5 card
  And Player 2 draw 5 card

 Scenario: Play one turn
   Given My game is started
   Then Random play turn
   And Random play turn

 Scenario: Reset a kingdom
  Given My game will reset kingdom
  Then Play a turn and reset
  Then Play a turn and reset