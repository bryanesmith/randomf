/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package createrandomdata;

import java.io.File;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author besmit
 */
public class MainTest extends TestCase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        Main.setTesting(true);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Main.setTesting(false);
    }

    @Before
    public void setUp() {
        Main.setTesting(true);
    }

    @After
    public void tearDown() {
        Main.setTesting(false);
    }

    /**
     * Test no parameter
     */
    @Test
    public void testNoParametersShouldFail() throws Exception {
        System.out.println("--- testNoParametersShouldFail() ---");
        Main.main(new String[0]);
    }
    
    @Test
    public void testHelp() throws Exception {
        System.out.println("--- testHelp() ---");
        String[] args1 = {
           "-h"  
        };
        Main.main(args1);
        
        String[] args2 = {
           "--help"  
        };
        Main.main(args2);
    }
    
    @Test
    public void testHelpPreventsOperation() throws Exception {
        System.out.println("--- testHelpPreventsOperation() ---");
        File testFile = null;
        try {
            testFile = new File("test1");
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            
            String[] args = {
                "--help", testFile.getAbsolutePath()
            };
            Main.main(args);
            assertFalse("File should not exist, operation should have been prevented by help: "+testFile.getAbsolutePath(),testFile.exists());
            
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
    
    @Test
    public void testDefaultFileSize() throws Exception {
        System.out.println("--- testDefaultFileSize() ---");
        File testFile = null;
        try {
            testFile = new File("test2");
            String[] args = {
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",1024*1024,testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }

    @Test
    public void test256KBShort() throws Exception {
        System.out.println("--- test256KBShort() ---");
        testCreateFileShortArg(256*1024);
    }
    @Test
    public void test256KBLong() throws Exception {
        System.out.println("--- test256KBLong() ---");
        testCreateFileLongArg(256*1024);
    }
    
    @Test
    public void test1MBShort() throws Exception {
        System.out.println("--- test1MBShort() ---");
        testCreateFileShortArg(1024*1024);
    }
    @Test
    public void test1MBLong() throws Exception {
        System.out.println("--- test1MBLong() ---");
        testCreateFileLongArg(1024*1024);
    }
    
    private void testCreateFileShortArg(long s) throws Exception {
        File testFile = null;
        try {
            testFile = new File("test"+s);
            String[] args = {
                "-s",
                String.valueOf(s),
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",s,testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
    
    public void testCreateFileLongArg(long s) throws Exception {
        File testFile = null;
        try {
            testFile = new File("test"+s);
            String[] args = {
                "--size",
                String.valueOf(s),
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",s,testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
    
    @Test
    public void testCreate456BString() throws Exception {
        File testFile = null;
        try {
            testFile = new File("test456");
            String[] args = {
                "-s",
                "456B",
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",456,testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
    
    @Test
    public void testCreate12KBString() throws Exception {
        File testFile = null;
        try {
            testFile = new File("test12KB");
            String[] args = {
                "-s",
                "12KB",
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",12*1024,testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
    
    @Test
    public void testCreate7MBString() throws Exception {
        File testFile = null;
        try {
            testFile = new File("test7M");
            String[] args = {
                "-s",
                "7MB",
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",7*1024*1024,testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
    
    @Test
    public void testCreateTenthOfGBString() throws Exception {
        File testFile = null;
        try {
            testFile = new File("test.1GB");
            String[] args = {
                "-s",
                ".1GB",
                testFile.getAbsolutePath()
            };
            
            assertFalse("Test file must not exist before test condition: "+testFile.getAbsolutePath(),testFile.exists());
            Main.main(args);
            
            assertTrue("File better exist.",testFile.exists());
            assertEquals("Expecting file of certain size.",(long)(.1*1024*1024*1024),testFile.length());
        } finally {
            if (testFile != null && testFile.exists()) {
                boolean deleted = testFile.delete();
                if (!deleted && testFile.exists()) {
                    System.err.println("Warning: could not delete "+testFile.getAbsolutePath());
                }
            }
        }
    }
}