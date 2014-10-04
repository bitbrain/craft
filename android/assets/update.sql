INSERT INTO `player` VALUES(1, 'localhost');


INSERT INTO `progress` VALUES(null, 1, "ALCHEMIST", 0.3, 3);
INSERT INTO `progress` VALUES(null, 1, "ENGINEER", 0.16, 5);
INSERT INTO `progress` VALUES(null, 1, "JEWELER", 0.57, 4);


INSERT INTO `item` VALUES("item_flux",    	"ico_flux.png", 	 1, "COMMON");
INSERT INTO `item` VALUES("item_acid_1",  	"ico_acid_1.png", 	 1, "COMMON");
INSERT INTO `item` VALUES("item_dust",    	"ico_dust.png", 	 1, "COMMON");
INSERT INTO `item` VALUES("item_water",   	"ico_water.png", 	 1, "COMMON");
INSERT INTO `item` VALUES("item_sulfur",  	"ico_sulfur.png", 	 1, "COMMON");
INSERT INTO `item` VALUES("item_mercury", 	"ico_mercury.png", 	 1, "COMMON");
INSERT INTO `item` VALUES("item_phiole_small", 	"ico_phiole_small.png",  1, "COMMON");
INSERT INTO `item` VALUES("item_graystone", 	"ico_graystone.png", 	 1, "RARE");
INSERT INTO `item` VALUES("item_xenocite", 	"ico_xenocite.png", 	 2, "RARE");
INSERT INTO `item` VALUES("item_acid_2", 	"ico_acid_2.png", 	 2, "COMMON");
INSERT INTO `item` VALUES("item_bentagon", 	"ico_bentagon.png", 	 2, "RARE");
INSERT INTO `item` VALUES("item_phiole_medium", "ico_phiole_medium.png", 2, "COMMON");
INSERT INTO `item` VALUES("item_darkstone",	"ico_darkstone.png", 	 2, "SUPERIOR");
INSERT INTO `item` VALUES("item_molten_sand", 	"ico_molten_sand.png", 	 2, "RARE");

INSERT INTO `owned_items` VALUES(null, "item_flux", 1, 7);
INSERT INTO `owned_items` VALUES(null, "item_acid_1", 1, 4);
INSERT INTO `owned_items` VALUES(null, "item_xenocite", 1, 1);
INSERT INTO `owned_items` VALUES(null, "item_dust", 1, 21);
INSERT INTO `owned_items` VALUES(null, "item_darkstone", 1, 1);

