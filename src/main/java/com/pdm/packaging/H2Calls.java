package com.pdm.packaging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class H2Calls {

    private Connection h2connection;

    public void createConnection(String location, String user, String password, int attempt) {
        String url = "jdbc:h2:" + location;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Error: environment not set up correct, could not find H2 drivers\n ");
            cnf.printStackTrace();
        }
        try {
            h2connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to H2 database established");
        } catch (SQLException se) {
            File path = new File(location);
            if (path.exists()) {
                attempt++;
                if (attempt < 6) {
                    System.out.println("Failed to connect to H2 database, attempting to recover (" + attempt + ")");
                    createConnection(location, user, password, attempt);
                } else {
                    System.out.println("Failed to connect to H2 database after 5 attempts, exiting...");
                    System.exit(1);
                }
            } else {
                System.out.println("Could not find " + location + ", attempting to create...");
                try {
                    if (path.mkdirs()) {
                        System.out.println(location + " created, attempting re-connect...");
                        createConnection(location, user, password, 0);

                    }
                } catch (SecurityException e) {
                    System.out.println("Failed to create location, application does not have permission");
                    System.exit(1);
                }

            }
        }
    }

    public Connection getH2connection() {
        return h2connection;
    }

    public void closeH2connection(){
        try {
            h2connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to close connection to H2 database");
        }
    }

    public void execute(String cmd) {
        try {
            Statement stmt = h2connection.createStatement();
            stmt.execute(cmd);
        } catch (Exception e) {
            System.out.println("Error executing statement '" + cmd + "\n" + e);
        }
    }

    public ResultSet query(String cmd) {
        ResultSet results = null;
        try {
            Statement stmt = h2connection.createStatement();
            results = stmt.executeQuery(cmd);
        } catch (Exception e) {
            System.out.println("Error querying statement '" + cmd + "'\n" + e);
        }
        return results;
    }

    public void initialize() {
        String table1 = "create table if not exists orders (order_ID integer primary key auto_increment, sender_ID integer not null, receiver_ID integer not null, is_prepaid bit default 0, cost decimal(20, 2), is_complete bit default 0 not null);";
        String table2 = "create table if not exists users (user_ID integer primary key auto_increment, name varchar(255) not null, is_premium bit default 0 not null, phone_number varchar(11), business_ID integer);";
        String table3 = "create table if not exists business (business_ID integer primary key auto_increment, name varchar(255) not null, address varchar(255) not null);";
        String table4 = "create table if not exists package (package_ID integer primary key auto_increment, order_ID integer not null, shipping_status varchar(1) not null, weight integer, delivery_time integer, trait varchar(1), tracking_ID integer not null);";
        String table5 = "create table if not exists traits (trait_ID varchar(1) primary key auto_increment, name varchar(255) not null);";
        String table6 = "create table if not exists tracking (tracking_ID integer primary key auto_increment, transport_ID integer not null, current_location_ID integer);";
        String table7 = "create table if not exists transport (transport_ID integer primary key auto_increment, type varchar(255) not null);";
        String table8 = "create table if not exists locations (location_ID integer primary key auto_increment, location_name varchar(255) not null);";
        String table9 = "create table if not exists stops (tracking_ID integer not null, location_ID integer not null, stop_num integer not null, primary key (tracking_ID,location_ID));";
        String table10 = "create table if not exists status (status_ID integer primary key auto_increment, description varchar(255) not null);";
        String users = "insert into users (name, is_premium, phone_number, business_ID) values ('Joy X. Sweeney', 1, 18655856463, 32),('Knox O. Obrien', 0, 19162014242, 41),('Caldwell S. Gillespie', 0, 19201806749, 29),('Cassady I. Pierce', 1, 17431991923, 9),('Vanna K. Bradley', 1, 16095199766, 34),('Anika N. Irwin', 0, 11571261241, 2),('Pandora K. Warren', 0, 17857523194, 46),('Ifeoma G. Oneal', 1, 12691045404, 48),('Garth T. Mitchell', 1, 16223998533, 10),('Cecilia J. Rivera', 0, 15142240367, 18),('Leonard F. Vargas', 0, 11564191010, 12),('Vernon M. Morton', 0, 11992184062, 50),('Tyrone B. Sanchez', 1, 16262186559, 41),('Noelle U. Mcbride', 0, 17624934917, 26),('Murphy K. Berger', 1, 11262773904, 44),('Davis Q. Holcomb', 0, 12376329355, 11),('Dominique G. Roach', 0, 15609628049, 5),('Deanna O. Castillo', 1, 14007465880, 29),('Sasha B. Tyler', 1, 15813095797, 35),('Evangeline R. Lancaster', 1, 12651412247, 25),('Thomas Q. Weaver', 1, 14457703377, 43),('Flynn V. Odom', 1, 17007453169, 42),('John N. Mcmillan', 0, 15654949369, 5),('Fitzgerald Y. Cooke', 0, 18852811130, 6),('Kane A. Figueroa', 0, 18184040508, 40),('Carla C. Gay', 0, 11943269790, 7),('Sean Y. Glover', 0, 13064468011, 13),('Gary V. Dorsey', 0, 11614417849, 2),('Richard K. Benton', 1, 12836002658, 16),('Gannon K. Newton', 1, 12591271186, 24),('Raymond R. Berg', 0, 16446359412, 29),('Venus C. Benton', 0, 17904493432, 17),('Lars T. Massey', 0, 17275581478, 35),('Lev W. Rios', 1, 13402044519, 38),('Stacy J. Hoover', 1, 18268612244, 40),('Hillary L. Sheppard', 0, 14305415433, 38),('Sacha M. Nash', 0, 19035474011, 16),('Serena X. Turner', 0, 17023343051, 41),('Joy O. Grimes', 0, 17861874111, 8),('Yeo J. Puckett', 1, 16517185344, 28),('Berk E. Castillo', 0, 19056080970, 18),('Alea I. Puckett', 1, 12805558478, 44),('Drake V. Lyons', 1, 12603480794, 47),('Rae R. Nixon', 0, 15992386767, 1),('Andrew A. Sanford', 1, 12675329684, 50),('Galvin Z. Weeks', 0, 17627571381, 7),('Scott F. Lawrence', 1, 16134637118, 1),('Jenette C. Lee', 1, 17985880262, 23),('Beverly K. Preston', 0, 12322494818, 9),('Eden L. Downs', 1, 16107142544, 39),('Jameson N. Cline', 1, 19363599993, 7),('Rana L. Whitehead', 0, 14008836445, 47),('Lars V. Yates', 1, 13842989035, 2),('Kenneth H. Barron', 0, 11824113689, 37),('Damian G. Benson', 1, 14507374124, 25),('Xena S. Alvarado', 0, 19486002273, 13),('Risa W. Alvarez', 0, 15451381498, 35),('Simone H. Ball', 1, 14647674050, 29),('Brian M. Moreno', 1, 18684133616, 48),('Ima I. Bray', 0, 13914255285, 8),('Galena T. Simon', 0, 12658386885, 25),('Alden S. Obrien', 0, 16906907039, 35),('Raymond X. Santos', 1, 16642118669, 33),('Aurora W. Hopkins', 1, 15394859541, 19),('Gareth H. Lane', 1, 12644628006, 10),('Hoyt U. Harrell', 0, 16914888713, 22),('Ina X. Savage', 0, 12151526630, 50),('Tatiana E. Yang', 0, 16708134678, 4),('Yolanda S. Peck', 0, 16423570280, 1),('Regan T. Lindsay', 1, 15305835979, 26),('Jorden U. Gentry', 0, 19149963528, 26),('Nyssa E. Schultz', 1, 19708438472, 48),('Scott K. Lynn', 0, 14929697601, 30),('Dane X. Turner', 0, 17638078557, 48),('Josephine R. Harrington', 1, 11345511760, 1),('Emmanuel K. Bush', 0, 18956582300, 12),('Maxine K. Hobbs', 0, 11574706822, 25),('Gil O. Morris', 1, 15646377472, 11),('Sean H. Farley', 1, 15376639480, 23),('Julie L. Boyd', 1, 19088528906, 31),('Finn M. Lane', 0, 14329834805, 17),('Boris P. Ortiz', 0, 19629012331, 6),('Nehru M. Greer', 0, 17158428582, 40),('Fiona D. Stuart', 0, 11841611586, 38),('Edward W. Cooke', 0, 12403428953, 8),('Cooper E. Roy', 1, 18754249281, 30),('Helen B. Ochoa', 1, 19904951683, 18),('Fletcher W. Martinez', 1, 12665782275, 12),('Venus M. Buck', 0, 12908636289, 44),('Kristen C. Holland', 1, 16243685902, 14),('Hope P. Bullock', 0, 15569011509, 17),('Cain T. Blanchard', 1, 19208931643, 28),('Shannon B. Hudson', 0, 12593370719, 3),('Neve F. Chandler', 1, 17348919281, 20),('Laurel Z. Hobbs', 0, 14146843785, 7),('Stuart H. Henson', 0, 14056131267, 49),('Deborah K. Cunningham', 0, 11841305165, 42),('Yoshi X. Vargas', 1, 15484886172, 38),('Erin A. Duke', 0, 16419207777, 43),('Marvin K. Beard', 0, 18523957786, 43),('Perry X. Simmons', 1, 12786208545, 46),('Amaya V. Burgess', 1, 19412480928, 1),('Vladimir C. Rosario', 0, 19788717447, 23),('Philip Q. Henderson', 1, 14071124393, 36),('Barrett L. Stevens', 0, 15903252446, 35),('Natalie T. Flowers', 1, 16687766623, 34),('Charissa C. Spears', 0, 19883124388, 11),('Solomon D. Harvey', 1, 15342168026, 5),('Caesar V. Wiggins', 1, 19279000894, 17),('Evangeline F. Martinez', 0, 14908193458, 39),('Isabella B. Barr', 0, 16186747627, 49),('Sylvester D. Gonzalez', 0, 18378037751, 4),('Nasim S. Hicks', 1, 12961171657, 22),('Calista K. Kelly', 1, 17818635715, 26),('Ezra Z. Kerr', 0, 15954305659, 8),('Erasmus P. Weeks', 1, 16153766268, 14),('Faith N. Dotson', 0, 16115318483, 17),('Faith H. Carson', 0, 14366415187, 12),('Ariana D. Richardson', 1, 19304822925, 39),('Jack M. Walker', 1, 17779021050, 43),('Iola C. Gutierrez', 1, 14747215474, 11),('Carly L. Price', 0, 17848023920, 31),('Keane K. Alvarado', 0, 16603284635, 11),('Xanthus A. Cunningham', 0, 12905846223, 6),('Brian Z. Herring', 1, 14969598458, 3),('Aristotle L. Jacobson', 1, 13022570027, 6),('Francis U. Love', 1, 18979479864, 7),('Burke S. Sharpe', 0, 17234206530, 34),('Upton W. Cline', 1, 12261652845, 11),('Hammett M. Potter', 0, 12752721464, 49),('Nyssa H. House', 1, 17294347127, 4),('Victor B. Bishop', 0, 14789056054, 43),('Ross X. Valencia', 1, 13881760091, 47),('Chandler H. Ball', 0, 18554737429, 43),('Shellie F. Lancaster', 0, 11615848494, 14),('Penelope G. Cox', 1, 14239139340, 12),('Sade B. Jones', 0, 17904214948, 45),('Robert A. Mcclain', 1, 17705176792, 37),('Reece C. Sullivan', 1, 19563482097, 44),('Jordan D. Bryan', 0, 16753389498, 14),('Amela R. Cash', 0, 15291771344, 6),('Dustin B. Christian', 1, 19966690350, 23),('Todd T. King', 1, 18632665863, 15),('Griffin H. Mckee', 0, 14334231183, 39),('Rafael Z. Ashley', 0, 13021396415, 9),('Sade L. Beck', 1, 19533657540, 35),('Xenos M. Miranda', 0, 19181868342, 49),('Fritz Y. Bender', 0, 14263138451, 49),('Flynn M. Chen', 1, 11361022926, 34),('Macey S. Sharpe', 0, 15603162937, 48),('Shelly N. Cline', 0, 16186983852, 46),('Lara Z. Pittman', 1, 15437216851, 40),('Mercedes L. Waller', 1, 15905766939, 34),('Mari E. Kemp', 0, 19207744972, 20),('Mia Y. Andrews', 0, 12891329954, 21),('Upton H. Carroll', 1, 13929290421, 8),('Abbot L. Wilkinson', 0, 15451366801, 24),('Thane T. Tran', 0, 17529134766, 21),('Chastity M. Hunter', 1, 12122749724, 15),('Hilda L. Briggs', 1, 17857530049, 36),('Tamara M. Graves', 1, 19822233376, 20),('Janna K. Buck', 0, 15054345201, 6),('Catherine A. Reese', 1, 12768548862, 11),('Griffith H. Velazquez', 1, 17426881809, 19),('Flynn T. May', 1, 11196388845, 38),('Felix L. Mooney', 0, 16878550977, 6),('Ella O. Dillon', 1, 11057665577, 37),('Nathan Q. Britt', 1, 16944704243, 4),('Kimberly I. Parsons', 0, 11707328691, 23),('MacKensie F. Marquez', 0, 12642948936, 8),('Amir Y. Figueroa', 1, 14728198064, 31),('Abbot J. Duran', 1, 16771145829, 22),('Hamilton K. Humphrey', 0, 14665973479, 9),('Alisa F. Vinson', 1, 15201729366, 40),('Glenna M. Dennis', 1, 17408660946, 8),('Megan B. Carson', 0, 15109119381, 29),('Hermione D. Bender', 0, 11486305887, 26),('Richard K. Howell', 0, 14067388322, 5),('William V. Owens', 1, 13819158451, 31),('Leigh W. Lee', 0, 12429332200, 14),('Donovan T. Barton', 0, 15749749342, 23),('Knox E. Bernard', 1, 18522606674, 2),('Sara A. Gardner', 1, 11625991748, 46),('Dana Y. Barron', 0, 13421883443, 48),('Genevieve Q. Armstrong', 0, 12925297182, 25),('Jescie M. Carney', 0, 18705401190, 34),('Arsenio C. York', 0, 13085437675, 44),('Porter T. Owen', 1, 18832877938, 40),('Lane Z. Avery', 1, 14707175276, 28),('September P. Witt', 1, 17136697475, 9),('Macaulay C. Byrd', 0, 13254489057, 16),('Ashton R. Shaw', 0, 19733289770, 50),('Kristen H. Meyer', 0, 12524398771, 15),('Evan P. Shepherd', 0, 11917738039, 37),('Jacqueline Z. Thomas', 1, 14725598656, 46),('Dante G. Christian', 0, 15516125142, 10),('Georgia X. Lowe', 1, 14285661180, 27),('Abigail I. Hudson', 0, 12346317873, 29),('Kelsie Y. Sanford', 0, 16352744798, 43),('Chastity J. Ball', 0, 19364466544, 18),('Willow O. Barton', 0, 13947937813, 12),('Pearl O. Salinas', 1, 11769273050, 11),('Ulric Z. Morrow', 1, 13554935956, 50),('Alice T. Bush', 1, 15474832159, 35),('Mary Z. Tran', 1, 19227821940, 50),('Medge T. Galloway', 1, 13677254010, 41),('Althea K. Gordon', 0, 19709335803, 11),('Avram Q. Boyd', 1, 16726279705, 39),('Zachary R. Taylor', 0, 12409018288, 22),('Jamal C. Hawkins', 0, 14631525539, 12),('Colton N. Bray', 0, 13248867702, 19),('Colorado D. Gentry', 0, 13091675596, 16),('Aline V. Marsh', 0, 15028663254, 26),('Phyllis L. Delgado', 0, 16165759473, 36),('Lydia G. Boone', 0, 19288105638, 7),('Zelda O. Fowler', 0, 12817039561, 4),('Henry Q. Chandler', 0, 16751028517, 49),('Abdul A. Hess', 0, 13535899361, 4),('Kirestin U. Duncan', 1, 13965955499, 5),('Cameran N. Bridges', 0, 14958903624, 2),('Levi Z. Mooney', 0, 15553194248, 32),('Flavia L. Gibson', 1, 17442332791, 5),('Taylor F. Gilliam', 1, 11499501767, 43),('Nehru H. Landry', 1, 14519981093, 17),('Lee K. Bender', 0, 12274488127, 44),('Irene D. Quinn', 1, 17526057258, 42),('Melvin X. Malone', 0, 16137840408, 33),('Colette J. Hunt', 1, 15314158982, 48),('Scarlett X. Rodgers', 1, 13893876044, 48),('Isaac P. Herrera', 0, 19511275095, 30),('Felix X. Valdez', 0, 17392297511, 16),('Igor H. Walter', 0, 19576216463, 13),('Germaine F. Prince', 0, 18959388161, 46),('Raven N. Saunders', 0, 12717364464, 48),('Vielka Y. Guerrero', 0, 15336379021, 28),('John X. Albert', 0, 18874959004, 39),('Tanek A. Preston', 1, 15334127972, 18),('Bruce I. Patrick', 1, 14077367833, 29),('Kelsie Z. Hughes', 1, 11613420536, 16),('Malachi N. Vang', 1, 12664272334, 31),('Nehru B. Huffman', 0, 15121367664, 43),('Ruby B. Harmon', 1, 14091950416, 12),('Kareem W. Hines', 1, 18061945012, 17),('Aladdin Q. Stark', 0, 15266635020, 40),('Kyle R. Sloan', 0, 14886725225, 34),('Grant R. Dominguez', 1, 12619458612, 31),('Aladdin H. Bolton', 1, 13354386267, 47),('Gretchen L. Ruiz', 0, 19945770381, 22),('Tarik O. James', 0, 13602952196, 8),('Suki J. Norman', 0, 12478151783, 1),('Octavius Q. Ratliff', 0, 14326125548, 21),('Lyle T. Ball', 1, 11245724780, 23),('Althea A. Burch', 0, 19985902406, 47),('Len U. May', 0, 16117712484, 18),('Cynthia O. Heath', 1, 17411561195, 22),('Xaviera B. Carrillo', 1, 19925350404, 14),('Shellie X. Kirk', 1, 16731705237, 38),('Roth U. Farrell', 1, 19542371945, 42),('Maya R. Yates', 0, 17441301447, 19),('Lyle B. Shaffer', 1, 19758503219, 44),('Winter O. Hamilton', 1, 19136404119, 28),('Hanna U. Kelley', 0, 17027036315, 38),('Zena O. Goodwin', 1, 11686913589, 44),('Lenore N. Ray', 0, 13945061205, 48),('Riley E. English', 1, 18718593659, 23),('Ora S. Holcomb', 0, 14805172612, 44),('Mona N. Randall', 1, 18171672665, 3),('Kareem J. Barrett', 1, 18906996866, 12),('Chelsea X. Coleman', 0, 14054211344, 14),('Quintessa V. Salazar', 1, 13234667157, 50),('Chastity B. Edwards', 0, 14751101129, 25),('Clarke N. Osborn', 0, 19703068267, 36),('Alfonso U. Wheeler', 0, 17096460834, 24),('John N. Morrow', 0, 11479331232, 34),('Reagan M. Juarez', 0, 18353166288, 25),('Alexander D. Juarez', 0, 14031650083, 48),('Kim W. Durham', 0, 19164828055, 50),('Jacqueline Y. Barry', 0, 13429140510, 35),('Hu H. Anthony', 1, 14447471402, 18),('Chadwick D. Rodgers', 0, 19073736655, 37),('Hedy Z. Nguyen', 1, 12079227972, 13),('Devin F. Russell', 0, 18755087571, 37),('Madeson I. Melton', 0, 16409430827, 39),('Beatrice K. Hines', 1, 12803090026, 41),('Cailin R. Knowles', 0, 16671561404, 36),('Tucker T. Murray', 0, 13813623657, 9),('Petra Y. Jensen', 0, 17896614670, 32),('Abel S. Shepard', 0, 19828156277, 34),('Lunea X. Carter', 1, 19205568291, 37),('Craig I. Bright', 1, 13277087113, 29),('Beau C. Powell', 1, 19378590650, 40),('Cameron O. May', 0, 16613897957, 39),('Plato I. Fry', 0, 13863061240, 10),('Adele S. Silva', 0, 14595911051, 42),('Lewis W. Briggs', 0, 18874641477, 37),('Colby V. Obrien', 0, 19008692701, 26),('Burton O. Skinner', 0, 18953266439, 21),('Bernard X. Elliott', 0, 14876501535, 26),('Karen P. Shepard', 0, 18404828790, 17),('Beck M. Fischer', 0, 14319540054, 13),('Pamela C. Ochoa', 1, 11666503061, 32),('Gregory P. Pacheco', 1, 16339334920, 32),('Amanda D. Mcleod', 1, 13012434760, 18),('Serena U. Murphy', 1, 15211577063, 17),('Bradley M. Hansen', 1, 12277919579, 30),('Nichole C. Barron', 0, 16672244186, 46),('Lila A. Sullivan', 1, 17229490273, 27),('Eve P. Waller', 1, 13464653756, 4),('Jack Z. Bernard', 0, 13765353848, 24),('Moses K. Maxwell', 1, 15663178857, 3),('Shelley V. Best', 1, 19842823072, 49),('Roanna S. Sykes', 0, 19211162803, 18),('Tyrone J. Schultz', 0, 14382668683, 38),('Arthur K. Byrd', 0, 14124122212, 10),('Plato I. Sanders', 0, 16158916540, 47),('Elijah E. Rodriquez', 0, 11393881197, 22),('Yael S. Roy', 0, 17047420616, 19),('Ann J. Manning', 0, 13801309583, 16),('Shay X. Hatfield', 0, 13493559936, 38),('Ocean P. Ayala', 1, 19099368358, 5),('Winifred B. Short', 1, 12472075499, 8),('Basil A. Cook', 1, 18269515973, 29),('Ezra R. Allison', 1, 16199574631, 30),('Veronica N. Holman', 0, 11904471521, 7),('Kaye G. Collins', 1, 19902999662, 28),('Levi D. Scott', 0, 16388129278, 49),('Brynn O. Hodge', 0, 16259520945, 26),('Phelan D. Herring', 1, 13439270108, 27),('Nadine E. Mcleod', 1, 17028371430, 49),('Pandora X. Luna', 0, 15486790347, 32),('Evan D. Rogers', 1, 13249603142, 44),('Shannon X. Dillard', 0, 19263250362, 46),('Nevada E. Knowles', 1, 13955969739, 29),('Regan X. Barlow', 0, 16174659125, 38),('Fuller X. Jordan', 1, 13924707850, 48),('Alexis R. Britt', 1, 19303302681, 17),('Ali E. Jordan', 0, 16897261732, 34),('Erica O. Burnett', 0, 16138430157, 46),('Libby N. Cobb', 1, 18622217526, 4),('Timon E. Dunlap', 0, 14712226024, 50),('Erich W. Espinoza', 1, 12342997080, 14),('Caryn K. Carlson', 0, 16996470574, 18),('Lev Z. Acosta', 1, 19524661823, 16),('Igor S. Lowery', 0, 14264960494, 15),('Gray A. Mercado', 0, 13997588851, 16),('Jeremy K. Alford', 0, 19201344068, 17),('Quin W. Mccall', 1, 15329422355, 1),('Jaquelyn B. Wilder', 1, 12638689808, 46),('Yolanda I. Craft', 0, 16403010765, 17),('Sage U. Dodson', 0, 17024358582, 31),('Kennan J. Walker', 1, 19487151588, 14),('Knox C. Dennis', 0, 11164135745, 30),('Kylan U. Rivera', 0, 14638125285, 36),('Chanda P. Short', 0, 12243782178, 19),('Hilel X. Peck', 1, 13159398543, 1),('Finn W. Holman', 1, 18994271653, 32),('Elvis R. Bennett', 0, 16039641750, 11),('Kai N. Campbell', 1, 18997880620, 47),('Steel J. Joyce', 1, 15374860737, 34),('Kibo U. Newton', 1, 13367685941, 19),('Aaron H. Christian', 1, 16169075776, 24),('Willa Y. Ford', 1, 11947857584, 42),('Callie Z. Fleming', 0, 11319108977, 39),('Griffin Y. Hull', 0, 17627161365, 30),('Joseph X. Mathis', 0, 13488447590, 34),('Nichole I. Sharp', 1, 16473484625, 49),('Evelyn H. Gates', 0, 19378775412, 12),('Illiana J. Oneal', 0, 17671163869, 18),('Vivian K. Marsh', 1, 17349684925, 23),('Silas U. Roberson', 1, 16727982848, 20),('Malcolm B. Holder', 1, 11152196898, 15),('Cullen M. Whitley', 0, 13196333149, 2),('Tobias A. Chang', 1, 16171545346, 1),('Paul G. Houston', 0, 17641065972, 26),('Cara Y. Shields', 1, 18683366064, 46),('Nathaniel T. Watts', 1, 13511078914, 4),('Violet B. Petty', 1, 16995774292, 32),('Veronica Z. Clark', 0, 12679063193, 26),('Athena L. Patrick', 1, 14265352251, 2),('Athena Z. Willis', 0, 13024921608, 13),('Derek R. Stark', 1, 11879025332, 39),('Ivan T. Levine', 0, 14589963752, 18),('Cheyenne A. Day', 1, 18533361380, 35),('Declan S. Woods', 0, 18507453830, 16),('Marcia L. Walters', 1, 15796612109, 2),('Chantale K. Conway', 1, 19649874969, 38),('Chaim S. Marshall', 0, 13509530971, 49),('Branden O. Dyer', 0, 14453269471, 17),('Alec R. Barlow', 1, 14033307128, 7),('Ebony V. Deleon', 1, 13587858771, 50),('Eden Q. Mcgee', 0, 12025335069, 32),('Eden A. Barton', 0, 14714026941, 26),('Ignatius E. Benton', 0, 13006305811, 44),('Brianna O. Saunders', 0, 19268955952, 30),('Victoria W. Navarro', 0, 14854933323, 10),('Logan A. Wilkinson', 0, 14496899769, 25),('Alisa D. Hancock', 0, 17893011828, 13),('Ishmael R. Barnett', 1, 14621720175, 34),('Bert G. Stein', 0, 17303696876, 44),('Asher G. Miles', 0, 15363884454, 10),('Bianca W. Wolfe', 0, 16716556591, 1),('Laurel U. Burgess', 1, 11346465332, 6),('Derek H. Mcclain', 1, 13093776193, 15),('Whoopi V. Love', 0, 11814073024, 14),('Alfreda R. Flynn', 1, 11975695545, 8),('Camden Y. Romero', 0, 11441521891, 36),('Ashton O. Robles', 0, 15685564714, 45),('Keelie I. Grimes', 0, 19479075730, 5),('Alice S. Moody', 0, 13375378873, 20),('Gary N. Golden', 1, 13972371069, 23),('Marcia P. Montgomery', 1, 13286256759, 7),('Florence K. Skinner', 0, 13399794780, 20),('Axel I. Le', 1, 14557347220, 8),('Roary S. Short', 1, 13021229126, 16),('Kato L. Head', 1, 16232336067, 38),('Aurora H. Obrien', 1, 19074659293, 7),('Leandra R. Sharp', 0, 11091667876, 33),('Mohammad T. Cannon', 0, 15677155821, 48),('Halee L. Taylor', 0, 12459732202, 41),('Kato Y. Davidson', 1, 15524493409, 35);";

        String businesses = "insert into business (name, address) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/business.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                businesses += "('" + split[1] + "', '" + split[2] + "'),";
            }
            businesses = businesses.substring(0, businesses.length() - 1);
            businesses = businesses + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String locations = "insert into locations (location_name) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/locations.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                locations += "('" + split[1] + "'),";
            }
            locations = locations.substring(0, locations.length() - 1);
            locations = locations + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String packages = "insert into package (order_ID, shipping_status, weight, delivery_time, trait, tracking_ID) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/package.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                packages += "(" + split[0] + ", " + split[1] + ", " + split[2] + ", " + split[3] + ", " + split[4] + ", " + split[5] + "),";
            }
            packages = packages.substring(0, packages.length() - 1);
            packages = packages + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orders = "insert into orders (sender_ID, receiver_ID, is_prepaid, cost, is_complete) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/orders.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                orders += "(" + split[1] + ", " + split[2] + ", " + split[3] + ", " + split[4] + ", " + split[5] + "),";
            }
            orders = orders.substring(0, orders.length() - 1);
            orders = orders + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tracking = "insert into tracking (transport_ID, current_location_ID) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/tracking.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                tracking += "(" + split[1] + ", " + split[2] + "),";
            }
            tracking = tracking.substring(0, tracking.length() - 1);
            tracking = tracking + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String traits = "insert into traits (name) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/traits.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                traits += "('" + split[1] + "'),";
            }
            traits = traits.substring(0, traits.length() - 1);
            traits = traits + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String transport = "insert into transport (type) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/transport.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                transport += "('" + split[1] + "'),";
            }
            transport = transport.substring(0, transport.length() - 1);
            transport = transport + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String status = "insert into status (description) values ";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./import/status.csv"));
            String line;
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                status += "('" + split[1] + "'),";
            }
            status = status.substring(0, status.length() - 1);
            status = status + ";";
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(table1);
        System.out.println(table2);
        System.out.println(table3);
        System.out.println(table4);
        System.out.println(table5);
        System.out.println(table6);
        System.out.println(table7);
        System.out.println(table8);
        System.out.println(table9);
        System.out.println(table10);
        System.out.println(users);
        System.out.println(businesses);
        System.out.println(locations);
        System.out.println(packages);
        System.out.println(orders);
        System.out.println(tracking);
        System.out.println(traits);
        System.out.println(transport);
        System.out.println(status);

//        execute(table1);
//        execute(table2);
//        execute(table3);
//        execute(table4);
//        execute(table5);
//        execute(table6);
//        execute(table7);
//        execute(table8);
//        execute(table9);
//        execute(table10);
        execute(users);
        execute(businesses);
        execute(locations);
        execute(packages);
        execute(orders);
        execute(tracking);
        execute(traits);
        execute(transport);
        execute(status);
    }

}
