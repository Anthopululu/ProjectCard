Feature: Draw Card

 Scenario: Game created
  Given My game is created
  Then Player 1 draw 5 card
  And Player 2 draw 5 card

 Scenario: Play one turn
   Given My game is started
   Then Random play turn player
   Then Random play turn player

