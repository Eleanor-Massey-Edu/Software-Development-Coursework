//Test suite for all unit tests

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;


@Suite
@IncludeTags("system")
@SelectClasses(testCard.class)
public class SystemTestSuite {
}

