package org.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

//@RunWith(Suite.class)
@Suite
@SelectPackages("org")
@IncludeTags({"Fast", "Registration"})
public class GatherTests {
}
