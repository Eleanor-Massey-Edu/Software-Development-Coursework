//Test suite for all unit tests

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;


@Suite
@IncludeTags("unit")
@SelectClasses({testCard.class, testTakeTurn.class, testPack.class, testPlayer.class, testCardDeck.class})
public class UnitTestSuite {
}

