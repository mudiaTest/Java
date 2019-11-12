package my.com.pl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import my.com.pl.component.TrailData;
import my.com.pl.config.TrailForksEnv;
import my.com.pl.srv.MapUpdaterSrv;
import my.com.pl.srv.TrailsSrv;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class MapUpdaterTests {

	
//	@Autowired
//	HttpReaderSrv ds;
//	@Autowired
//	Zip7Srv us;
//	@Autowired
//	GMTService gs;	
//	@Autowired
//	HttpReaderSrv hrs;	
	@Autowired
	MapUpdaterSrv mus;
	@Autowired
	TrailForksEnv tfv;
	
	String txt = 
"{\"error\":0\n" + 
"\"message\":\"\"\n" + 
"\"data\":[{\"trailid\":\"3\"\n" + 
"\"vid\":\"4\"\n" + 
"\"title\":\"Floppy Bunny\"\n" + 
"\"rid\":\"1\"\n" + 
"\"difficulty\":\"5\"\n" + 
"\"activitytype\":1\n" + 
"\"trailtype\":\"1\"\n" + 
"\"biketype\":\"1,2\"\n" + 
"\"physical_rating\":\"2\"\n" + 
"\"ttfs\":\"16,15\"\n" + 
"\"wet_weather\":\"1\"\n" + 
"\"season\":\"\"\n" + 
"\"unsanctioned\":\"0\"\n" + 
"\"hidden\":\"0\"\n" + 
"\"rating\":\"81\"\n" + 
"\"votes\":\"89\"\n" + 
"\"ridden\":\"2256\"\n" + 
"\"faved\":\"9\"\n" + 
"\"status\":\"1\"\n" + 
"\"products\":\"\"\n" + 
"\"total_comments\":\"12\"\n" + 
"\"total_photos\":\"340\"\n" + 
"\"total_videos\":\"42\"\n" + 
"\"total_reports\":\"226\"\n" + 
"\"total_poi\":\"19\"\n" + 
"\"total_supporters\":\"11\"\n" + 
"\"total_osmways\":\"1\"\n" + 
"\"created\":\"1312869649\"\n" + 
"\"changed\":\"1557963188\"\n" + 
"\"last_comment_ts\":\"1312869649\"\n" + 
"\"last_report_ts\":\"1569305420\"\n" + 
"\"last_totals_ts\":\"1569614730\"\n" + 
"\"alias\":\"floppy-bunny\"\n" + 
"\"approved\":\"1\"\n" + 
"\"userid\":\"454369\"\n" + 
"\"published\":\"1\"\n" + 
"\"dirty\":\"0\"\n" + 
"\"views\":\"42576\"\n" + 
"\"trackid\":\"121144\"\n" + 
"\"condition\":\"4\"\n" + 
"\"confirmid\":\"0\"\n" + 
"\"download_disable\":\"0\"\n" + 
"\"usage\":\"3\"\n" + 
"\"direction\":\"2\"\n" + 
"\"opendate\":\"0\"\n" + 
"\"strava_segment\":\"0\"\n" + 
"\"strava_segment_reverse\":\"0\"\n" + 
"\"legacy_id\":\"0\"\n" + 
"\"closed\":\"0\"\n" + 
"\"climb_difficulty\":\"0\"\n" + 
"\"land_manager\":\"District of North Vancouver\"\n" + 
"\"land_manager_manual\":\"1\"\n" + 
"\"refnum\":\"\"\n" + 
"\"global_rank\":\"52\"\n" + 
"\"global_rank_score\":\"84.888\"\n" + 
"\"total_checkins\":\"6503\"\n" + 
"\"total_ridelogs\":\"6347\"\n" + 
"\"land_manager_url\":\"\"\n" + 
"\"aka\":\"\"\n" + 
"\"search_terms\":\"\"\n" + 
"\"search\":\"Floppy Bunny\"\n" + 
"\"featured_photo\":\"0\"\n" + 
"\"featured_video\":\"0\"\n" + 
"\"skidmap_id\":\"0\"\n" + 
"\"osmway\":\"0\"\n" + 
"\"password\":\"\"\n" + 
"\"funding_goal\":\"1000\"\n" + 
"\"funding_usd\":\"0\"\n" + 
"\"funding_currency\":\"\"\n" + 
"\"watchmen\":\"1\"\n" + 
"\"cleanup\":\"0\"\n" + 
"\"cleanup2\":\"0\"\n" + 
"\"cleanup3\":\"\"\n" + 
"\"leaderboard_disable\":\"0\"\n" + 
"\"popularity_colour\":\"ff3800\"\n" + 
"\"popularity_score\":\"90\"\n" + 
"\"season_type\":\"0\"\n" + 
"\"alpine\":\"0\"\n" + 
"\"ebike\":\"0\"\n" + 
"\"color\":\"\"\n" + 
"\"archived\":\"0\"\n" + 
"\"disable_sensitive_check\":\"0\"\n" + 
"\"planned\":\"0\"\n" + 
"\"family_friendly\":\"0\"\n" + 
"\"total_socialmedia\":\"0\"\n" + 
"\"hide_association\":\"0\"\n" + 
"\"cover_photo\":\"14704594\"\n" + 
"\"trail_association\":\"0\"\n" + 
"\"direction_flagged\":\"0\"\n" + 
"\"direction_forward\":\"100\"\n" + 
"\"direction_backward\":\"0\"\n" + 
"\"inventory_exclude\":\"0\"\n" + 
"\"difficulty_user_avg\":\"4\"\n" + 
"\"difficulty_votes\":\"4\"\n" + 
"\"imagemap3\":\"1\"\n" + 
"\"connector\":\"0\"\n" + 
"\"sac_scale\":\"1\"\n" + 
"\"trail_visibility\":\"0\"\n" + 
"\"datasource\":\"tf\"\n" + 
"\"verified\":\"1\"\n" + 
"\"snow_grooming\":\"0\"\n" + 
"\"osm_import_job\":\"0\"\n" + 
"\"dogs_allowed\":\"1\"\n" + 
"\"restricted_access\":\"0\"\n" + 
"\"amtb_rating\":\"0\"\n" + 
"\"id\":\"3\"\n" + 
"\"type\":\"trail\"\n" + 
"\"description\":\"Short fast trail with optional jumps and drops. A few mandatory wooden trail features. The trail is great for new riders to sample what they might encounter higher up the mountain.\"\n" + 
"\"access_info\":\"Accessed off Mtn. Hwy two trails before the 2nd switchback. Exiting at the water tower/turnaround.\"\n" + 
"\"source_text\":\"\"\n" + 
"\"source_link\":\"\"\n" + 
"\"disclaimer\":\"\"\n" + 
"\"amtb_info\":\"\"\n" + 
"\"act_mtb\":\"1\"\n" + 
"\"act_ebike\":\"0\"\n" + 
"\"act_hike\":\"1\"\n" + 
"\"act_moto\":\"0\"\n" + 
"\"act_trailrun\":\"1\"\n" + 
"\"act_atv\":\"0\"\n" + 
"\"act_snowshoe\":\"0\"\n" + 
"\"act_skialpine\":\"0\"\n" + 
"\"act_skixc\":\"0\"\n" + 
"\"act_skibc\":\"0\"\n" + 
"\"act_horse\":\"0\"\n" + 
"\"act_snowmobile\":\"0\"\n" + 
"\"act_mototrials\":\"0\"\n" + 
"\"latitude\":\"49.359060\"\n" + 
"\"longitude\":\"-123.040280\"\n" + 
"\"activitytypes\":[1,6,5]\n" + 
"\"track\":{\"latitude\":\"49.35906,49.359,49.35889,49.35877,49.35876,49.35877,49.35884,49.3589,49.35885,49.3589,49.35895,49.35903,49.35918,49.35922,49.35925,49.35924,49.35919,49.35913,49.3591,49.35907,49.35907,49.35913,49.35919,49.35925,49.35931,49.3593,49.35931,49.35934,49.35939,49.35949,49.35955,49.35962,49.35968,49.35967,49.35961,49.35958,49.35961,49.35965,49.35975,49.35985,49.3599,49.35995,49.35998,49.35994,49.35987,49.35986,49.35991,49.35998,49.36011,49.36025,49.36031\"\n" + 
"\"longitude\":\"-123.04028,-123.0401,-123.03964,-123.03955,-123.03947,-123.03934,-123.03908,-123.03862,-123.03841,-123.03827,-123.03819,-123.03821,-123.03811,-123.03805,-123.03794,-123.03783,-123.03776,-123.03779,-123.03778,-123.03773,-123.03767,-123.0375,-123.03742,-123.03741,-123.03733,-123.03715,-123.0371,-123.03707,-123.03706,-123.03708,-123.03712,-123.03709,-123.03697,-123.03689,-123.03681,-123.0367,-123.03658,-123.03651,-123.03648,-123.03649,-123.03652,-123.03647,-123.03635,-123.03623,-123.03618,-123.03609,-123.03597,-123.03586,-123.03577,-123.0357,-123.03571\"\n" + 
"\"altitude\":\"425,424.066,418.173,416.787,415.875,413.958,410.314,403.166,399.205,396.922,395.349,396.182,394.821,393.772,391.799,389.236,387.605,388.077,387.669,386.329,384.931,381.459,379.805,379.921,378.407,374.806,373.991,373.642,373.758,374.69,375.738,375.622,373.874,372.585,371.303,369.573,367.651,367.041,367.06,367.492,368.033,367.334,365.237,363.14,362.49,361.443,358.96,356.565,354.291,352.93,353.041\"\n" + 
"\"distance\":\"0,14.635,50.102,64.943,70.839,80.314,100.677,134.632,150.815,162.371,170.396,179.403,197.575,203.789,212.42,220.459,227.978,234.99,238.401,243.321,247.664,261.659,270.49,277.197,286.028,299.103,302.889,306.868,312.471,323.677,330.946,339.022,349.971,355.867,364.698,373.329,382.632,389.372,400.695,411.831,417.796,424.427,433.73,443.487,452.066,458.674,468.984,480.115,495.962,512.324,519.031\"\n" + 
"\"encodedPath\":\"cmglHvh~mVJc@T{AVQ@OAYMs@K{AHi@I[IOOB]SGKEU@UHMJDDADI?KKa@KOKAKO@c@AIEEIASBKFMEKW@OJODUEWGMSES@IDIIEWFWLI@QIWMUYQ[MK@\"\n" + 
"\"encodedLevels\":\"P?AAC??AE?BA@C?BA?A?CA@?B?C?@?@CA??ACA@?ACA@@AC@A@P\"}}\n" + 
",\n" + 
"{\"trailid\":\"5\"\n" + 
"\"vid\":\"3\"\n" + 
"\"title\":\"Pipeline\"\n" + 
"\"rid\":\"1\"\n" + 
"\"difficulty\":\"5\"\n" + 
"\"activitytype\":1\n" + 
"\"trailtype\":\"1\"\n" + 
"\"biketype\":\"1,2\"\n" + 
"\"physical_rating\":\"2\"\n" + 
"\"ttfs\":\"54,5,15,19\"\n" + 
"\"wet_weather\":\"1\"\n" + 
"\"season\":\"Year round\"\n" + 
"\"unsanctioned\":\"0\"\n" + 
"\"hidden\":\"0\"\n" + 
"\"rating\":\"86\"\n" + 
"\"votes\":\"98\"\n" + 
"\"ridden\":\"1527\"\n" + 
"\"faved\":\"9\"\n" + 
"\"status\":\"1\"\n" + 
"\"products\":\"\"\n" + 
"\"total_comments\":\"4\"\n" + 
"\"total_photos\":\"364\"\n" + 
"\"total_videos\":\"49\"\n" + 
"\"total_reports\":\"179\"\n" + 
"\"total_poi\":\"1\"\n" + 
"\"total_supporters\":\"3\"\n" + 
"\"total_osmways\":\"1\"\n" + 
"\"created\":\"1315883527\"\n" + 
"\"changed\":\"1547482936\"\n" + 
"\"last_comment_ts\":\"1313142894\"\n" + 
"\"last_report_ts\":\"1569125069\"\n" + 
"\"last_totals_ts\":\"1569382236\"\n" + 
"\"alias\":\"pipeline\"\n" + 
"\"approved\":\"1\"\n" + 
"\"userid\":\"454369\"\n" + 
"\"published\":\"1\"\n" + 
"\"dirty\":\"0\"\n" + 
"\"views\":\"27555\"\n" + 
"\"trackid\":\"140967\"\n" + 
"\"condition\":\"4\"\n" + 
"\"confirmid\":\"0\"\n" + 
"\"download_disable\":\"0\"\n" + 
"\"usage\":\"2\"\n" + 
"\"direction\":\"2\"\n" + 
"\"opendate\":\"0\"\n" + 
"\"strava_segment\":\"0\"\n" + 
"\"strava_segment_reverse\":\"0\"\n" + 
"\"legacy_id\":\"0\"\n" + 
"\"closed\":\"0\"\n" + 
"\"climb_difficulty\":\"0\"\n" + 
"\"land_manager\":\"Grouse (top 20m) + District of North Vancouver\"\n" + 
"\"land_manager_manual\":\"1\"\n" + 
"\"refnum\":\"\"\n" + 
"\"global_rank\":\"100\"\n" + 
"\"global_rank_score\":\"76.267\"\n" + 
"\"total_checkins\":\"2624\"\n" + 
"\"total_ridelogs\":\"2751\"\n" + 
"\"land_manager_url\":\"\"\n" + 
"\"aka\":\"\"\n" + 
"\"search_terms\":\"\"\n" + 
"\"search\":\"Pipeline\"\n" + 
"\"featured_photo\":\"0\"\n" + 
"\"featured_video\":\"0\"\n" + 
"\"skidmap_id\":\"0\"\n" + 
"\"osmway\":\"0\"\n" + 
"\"password\":\"\"\n" + 
"\"funding_goal\":\"1000\"\n" + 
"\"funding_usd\":\"0\"\n" + 
"\"funding_currency\":\"\"\n" + 
"\"watchmen\":\"1\"\n" + 
"\"cleanup\":\"0\"\n" + 
"\"cleanup2\":\"0\"\n" + 
"\"cleanup3\":\"\"\n" + 
"\"leaderboard_disable\":\"0\"\n" + 
"\"popularity_colour\":\"ff6b00\"\n" + 
"\"popularity_score\":\"80\"\n" + 
"\"season_type\":\"0\"\n" + 
"\"alpine\":\"0\"\n" + 
"\"ebike\":\"0\"\n" + 
"\"color\":\"\"\n" + 
"\"archived\":\"0\"\n" + 
"\"disable_sensitive_check\":\"0\"\n" + 
"\"planned\":\"0\"\n" + 
"\"family_friendly\":\"0\"\n" + 
"\"total_socialmedia\":\"0\"\n" + 
"\"hide_association\":\"1\"\n" + 
"\"cover_photo\":\"14537621\"\n" + 
"\"trail_association\":\"0\"\n" + 
"\"direction_flagged\":\"0\"\n" + 
"\"direction_forward\":\"100\"\n" + 
"\"direction_backward\":\"0\"\n" + 
"\"inventory_exclude\":\"0\"\n" + 
"\"difficulty_user_avg\":\"5\"\n" + 
"\"difficulty_votes\":\"3\"\n" + 
"\"imagemap3\":\"1\"\n" + 
"\"connector\":\"0\"\n" + 
"\"sac_scale\":\"0\"\n" + 
"\"trail_visibility\":\"0\"\n" + 
"\"datasource\":\"tf\"\n" + 
"\"verified\":\"1\"\n" + 
"\"snow_grooming\":\"0\"\n" + 
"\"osm_import_job\":\"0\"\n" + 
"\"dogs_allowed\":\"1\"\n" + 
"\"restricted_access\":\"0\"\n" + 
"\"amtb_rating\":\"0\"\n" + 
"\"id\":\"5\"\n" + 
"\"type\":\"trail\"\n" + 
"\"description\":\"Contains optional TTFs, steep rough terrain and moderately steep natural TTFs. Quite technical. Definitely very different from the new school trails and a step up in difficulty as well. It is well maintained and can be ridden with a decent amount of speed. A good introduction to the more challenging old-school trails.\"\n" + 
"\"access_info\":\"Accessed off Mtn Hwy past the 5th switchback at the large clearing about 20 meters\\r\\npast [L=https://www.trailforks.com/trails/ladies-only/] Ladies Only[/L].\"\n" + 
"\"source_text\":\"\"\n" + 
"\"source_link\":\"\"\n" + 
"\"disclaimer\":\"\"\n" + 
"\"amtb_info\":\"\"\n" + 
"\"act_mtb\":\"1\"\n" + 
"\"act_ebike\":\"0\"\n" + 
"\"act_hike\":\"1\"\n" + 
"\"act_moto\":\"0\"\n" + 
"\"act_trailrun\":\"1\"\n" + 
"\"act_atv\":\"0\"\n" + 
"\"act_snowshoe\":\"0\"\n" + 
"\"act_skialpine\":\"0\"\n" + 
"\"act_skixc\":\"0\"\n" + 
"\"act_skibc\":\"0\"\n" + 
"\"act_horse\":\"0\"\n" + 
"\"act_snowmobile\":\"0\"\n" + 
"\"act_mototrials\":\"0\"\n" + 
"\"latitude\":\"49.358997\"\n" + 
"\"longitude\":\"-123.049783\"\n" + 
"\"activitytypes\":[1,6,5]\n" + 
"\"track\":{\"latitude\":\"49.358997,49.35887,49.3588,49.35868,49.35858,49.35842,49.35823,49.35807,49.35794,49.35778,49.35769,49.35737,49.35723,49.35711,49.357,49.357,49.35703,49.3571,49.35716,49.35716,49.3571,49.35698,49.35664,49.35641,49.35617,49.35601,49.35575,49.35558,49.3555,49.35542,49.35533,49.35529,49.35511,49.35495,49.35479,49.35464,49.35456,49.35432,49.35403,49.3539,49.3538,49.3537,49.35361,49.35351,49.35343,49.35338,49.35319,49.35302,49.3529,49.3527,49.35249,49.35233,49.35221,49.35209,49.35194,49.35161,49.35142\"\n" + 
"\"longitude\":\"-123.049783,-123.0496,-123.04953,-123.04954,-123.04961,-123.04965,-123.04953,-123.04934,-123.04926,-123.04913,-123.04901,-123.04825,-123.04803,-123.04798,-123.0479,-123.04775,-123.04763,-123.04747,-123.04716,-123.04706,-123.04689,-123.04679,-123.04696,-123.04691,-123.04674,-123.04672,-123.0466,-123.04636,-123.04614,-123.04597,-123.04583,-123.04571,-123.04547,-123.04536,-123.04539,-123.04554,-123.04566,-123.0462,-123.04694,-123.04718,-123.04729,-123.0473,-123.04728,-123.04719,-123.04705,-123.04674,-123.04619,-123.04597,-123.04577,-123.04553,-123.04537,-123.04533,-123.04535,-123.04534,-123.04526,-123.04517,-123.04514\"\n" + 
"\"altitude\":\"588.953,587.007,585.783,585.201,585.434,584.968,582.463,578.915,576.468,573.09,570.296,553.59,546.218,542.768,539.624,536.447,534.211,531.734,526.11,524.772,521.205,518.885,517.02,514.335,510.856,509.331,504.045,499.277,495.316,491.894,489.129,487.703,483.933,482.423,482,481.394,480.228,474.027,471.986,471,469.311,467.039,464.825,461.971,459.291,456.32,449.681,445.718,442.922,439.209,435.135,431.64,428.251,425.047,421.44,412.828,407.061\"\n" + 
"\"distance\":\"0,19.355,28.638,41.993,54.206,72.22,95.05,117.528,133.092,153.208,166.454,231.956,254.218,268.035,281.561,292.418,301.721,315.672,339.08,346.318,360.313,375.486,415.222,441.036,470.408,488.247,518.417,544.081,562.319,577.5,591.738,601.495,627.988,647.469,665.381,685.274,697.703,745.022,807.534,830.128,843.799,854.935,865.04,877.921,891.402,914.519,959.584,984.292,1003.975,1032.184,1058.236,1076.25,1089.663,1103.018,1120.664,1157.91,1179.135\"\n" + 
"\"encodedPath\":\"ulglHdd`nVVe@LMV@RL^Fd@W^e@XO^YPW~@wCZk@VITO?]EWM_@K}@?SJa@VSbA`@l@In@a@^Cr@W`@o@Nk@Na@P[FWb@o@^U^D\\\\\\\\NVn@jBx@rCXn@RTR@PCRQN[H}@d@mB`@k@Vg@f@o@h@_@^GVBVA\\\\O`AQd@E\"\n" + 
"\"encodedLevels\":\"P?C@@C@@?@EBA?D@?@B@DBC@A@DA???B@FA?C?B@AF@AB@B??DB@?A@?P\"}},{\"trailid\":\"6\"\n" + 
"\"vid\":\"3\"\n" + 
"\"title\":\"Bobsled\"\n" + 
"\"rid\":\"1\"\n" + 
"\"difficulty\":\"4\"\n" + 
"\"activitytype\":1\n" + 
"\"trailtype\":\"2\"\n" + 
"\"biketype\":\"1,2,7\"\n" + 
"\"physical_rating\":\"1\"\n" + 
"\"ttfs\":\"1,54,17,104,21\"\n" + 
"\"wet_weather\":\"1\"\n" + 
"\"season\":\"year round\"\n" + 
"\"unsanctioned\":\"0\"\n" + 
"\"hidden\":\"0\"\n" + 
"\"rating\":\"90\"\n" + 
"\"votes\":\"181\"\n" + 
"\"ridden\":\"3572\"\n" + 
"\"faved\":\"23\"\n" + 
"\"status\":\"1\"\n" + 
"\"products\":\"\"\n" + 
"\"total_comments\":\"12\"\n" + 
"\"total_photos\":\"360\"\n" + 
"\"total_videos\":\"156\"\n" + 
"\"total_reports\":\"419\"\n" + 
"\"total_poi\":\"5\"\n" + 
"\"total_supporters\":\"10\"\n" + 
"\"total_osmways\":\"1\"\n" + 
"\"created\":\"1313143363\"\n" + 
"\"changed\":\"1557963384\"\n" + 
"\"last_comment_ts\":\"1315895926\"\n" + 
"\"last_report_ts\":\"1567966566\"\n" + 
"\"last_totals_ts\":\"1569614730\"\n" + 
"\"alias\":\"bobsled\"\n" + 
"\"approved\":\"1\"\n" + 
"\"userid\":\"454369\"\n" + 
"\"published\":\"1\"\n" + 
"\"dirty\":\"0\"\n" + 
"\"views\":\"53774\"\n" + 
"\"trackid\":\"114986\"\n" + 
"\"condition\":\"5\"\n" + 
"\"confirmid\":\"0\"\n" + 
"\"download_disable\":\"0\"\n" + 
"\"usage\":\"1\"\n" + 
"\"direction\":\"1\"\n" + 
"\"opendate\":\"0\"\n" + 
"\"strava_segment\":\"0\"\n" + 
"\"strava_segment_reverse\":\"0\"\n" + 
"\"legacy_id\":\"0\"\n" + 
"\"closed\":\"0\"\n" + 
"\"climb_difficulty\":\"0\"\n" + 
"\"land_manager\":\"District of North Vancouver\"\n" + 
"\"land_manager_manual\":\"1\"\n" + 
"\"refnum\":\"\"\n" + 
"\"global_rank\":\"1\"\n" + 
"\"global_rank_score\":\"121.769\"\n" + 
"\"total_checkins\":\"15455\"\n" + 
"\"total_ridelogs\":\"15166\"\n" + 
"\"land_manager_url\":\"http://www.dnv.org/recreation-and-leisure\"\n" + 
"\"aka\":\"\"\n" + 
"\"search_terms\":\"\"\n" + 
"\"search\":\"Bobsled\"\n" + 
"\"featured_photo\":\"0\"\n" + 
"\"featured_video\":\"0\"\n" + 
"\"skidmap_id\":\"0\"\n" + 
"\"osmway\":\"0\"\n" + 
"\"password\":\"\"\n" + 
"\"funding_goal\":\"3000\"\n" + 
"\"funding_usd\":\"2201.4\"\n" + 
"\"funding_currency\":\"CAD\"\n" + 
"\"watchmen\":\"1\"\n" + 
"\"cleanup\":\"0\"\n" + 
"\"cleanup2\":\"0\"\n" + 
"\"cleanup3\":\"\"\n" + 
"\"leaderboard_disable\":\"0\"\n" + 
"\"popularity_colour\":\"ff0500\"\n" + 
"\"popularity_score\":\"100\"\n" + 
"\"season_type\":\"0\"\n" + 
"\"alpine\":\"0\"\n" + 
"\"ebike\":\"0\"\n" + 
"\"color\":\"\"\n" + 
"\"archived\":\"0\"\n" + 
"\"disable_sensitive_check\":\"0\"\n" + 
"\"planned\":\"0\"\n" + 
"\"family_friendly\":\"0\"\n" + 
"\"total_socialmedia\":\"0\"\n" + 
"\"hide_association\":\"0\"\n" + 
"\"cover_photo\":\"12894813\"\n" + 
"\"trail_association\":\"0\"\n" + 
"\"direction_flagged\":\"0\"\n" + 
"\"direction_forward\":\"100\"\n" + 
"\"direction_backward\":\"0\"\n" + 
"\"inventory_exclude\":\"0\"\n" + 
"\"difficulty_user_avg\":\"3\"\n" + 
"\"difficulty_votes\":\"11\"\n" + 
"\"imagemap3\":\"1\"\n" + 
"\"connector\":\"0\"\n" + 
"\"sac_scale\":\"0\"\n" + 
"\"trail_visibility\":\"0\"\n" + 
"\"datasource\":\"tf\"\n" + 
"\"verified\":\"1\"\n" + 
"\"snow_grooming\":\"0\"\n" + 
"\"osm_import_job\":\"0\"\n" + 
"\"dogs_allowed\":\"1\"\n" + 
"\"restricted_access\":\"0\"\n" + 
"\"amtb_rating\":\"0\"\n" + 
"\"id\":\"6\"\n" + 
"\"type\":\"trail\"\n" + 
"\"description\":\"The first sanctioned new school flow trail, was rebuilt by the District of North Vancouver Trail crew in 2010. The proof is in the pudding of its popularity.  On a nice day, you literally need to take a number for the flow show: big berms, small doubles and buff, undulating trail will have you climbing back up for another lap.  Barely 10 minutes up [L=https://www.trailforks.com/trails/mountain-highway/]Mountain Highway[/L], it is easily accessed.  It's importance lies not only in its popularity, but it proves new school trails can be built on the Shore.\\r\\n\\r\\nEntirely pumped and some good carved berms. Has a wood wall ride, that must be ridden cautiously when wet. \\r\\nExtensive work has been invested in the trail via the NSMBA TAP program including a significant reroute near the bottom to bring the grade in line with the goal of the trail to serve beginners.\"\n" + 
"\"access_info\":\"Accessed before the 2nd switchback, exits above the [L=https://www.trailforks.com/trails/baden-powell/]Baden Powell[/L].\"\n" + 
"\"source_text\":\"\"\n" + 
"\"source_link\":\"\"\n" + 
"\"disclaimer\":\"\"\n" + 
"\"amtb_info\":\"\"\n" + 
"\"act_mtb\":\"1\"\n" + 
"\"act_ebike\":\"0\"\n" + 
"\"act_hike\":\"0\"\n" + 
"\"act_moto\":\"0\"\n" + 
"\"act_trailrun\":\"0\"\n" + 
"\"act_atv\":\"0\"\n" + 
"\"act_snowshoe\":\"0\"\n" + 
"\"act_skialpine\":\"0\"\n" + 
"\"act_skixc\":\"0\"\n" + 
"\"act_skibc\":\"0\"\n" + 
"\"act_horse\":\"0\"\n" + 
"\"act_snowmobile\":\"0\"\n" + 
"\"act_mototrials\":\"0\"\n" + 
"\"latitude\":\"49.358170\"\n" + 
"\"longitude\":\"-123.040960\"\n" + 
"\"activitytypes\":[1]\n" + 
"\"track\":{\"latitude\":\"49.35817,49.35813,49.35808,49.35798,49.35793,49.35813,49.35816,49.35811,49.357977,49.35784,49.35779,49.35783,49.35799,49.35802,49.358106,49.35821,49.358302,49.35838,49.358389,49.358291,49.35817,49.358047,49.35792,49.357812,49.35773,49.35773,49.35783,49.35792,49.35799,49.358,49.35797,49.35791,49.35783,49.357683,49.35755,49.35747,49.35748,49.35752,49.35762,49.3576,49.3574,49.35724,49.3572,49.3572,49.35725,49.357292,49.357316,49.35725,49.357243,49.35733,49.357442,49.357466,49.357386,49.357183,49.357119,49.356999,49.356919,49.356879,49.356812,49.356679,49.356559,49.356539,49.356469,49.356489\"\n" + 
"\"longitude\":\"-123.04096,-123.04091,-123.04088,-123.04091,-123.04088,-123.04075,-123.04064,-123.04058,-123.04054,-123.04046,-123.0404,-123.04031,-123.04018,-123.03995,-123.039869,-123.03982,-123.039767,-123.03968,-123.03959,-123.039456,-123.03938,-123.039381,-123.03935,-123.039274,-123.03914,-123.03898,-123.03884,-123.03877,-123.03864,-123.03855,-123.03842,-123.03839,-123.03839,-123.03841,-123.03852,-123.03851,-123.03835,-123.03824,-123.03808,-123.03804,-123.03805,-123.03801,-123.03796,-123.0379,-123.03781,-123.037616,-123.037423,-123.037257,-123.037096,-123.036935,-123.036812,-123.036721,-123.036683,-123.036721,-123.036771,-123.036671,-123.036691,-123.036641,-123.03663,-123.036861,-123.036971,-123.036961,-123.036741,-123.036471\"\n" + 
"\"altitude\":\"432.449,430.9,429.619,429.152,428.072,427.208,425.118,423.704,421.524,419.243,417.841,416.863,416.261,414.036,414.718,414.786,414.562,414.525,413.788,411.344,408.495,406.802,404.545,401.317,397.673,394.876,393.332,393.527,392.478,390.678,387.295,385.902,384.77,382.939,382.692,381.119,377.942,376.549,375.225,374.177,370.731,366.634,364.823,363.89,363.703,361.419,358.431,353.265,351.226,350.26,351.329,350.745,348.257,344.768,344.401,337.703,335.107,332.777,330.505,333.035,333.072,332.18,324.169,318.529\"\n" + 
"\"distance\":\"0,5.732,11.697,23.02,28.985,53.12,61.751,68.803,83.864,100.152,107.204,115.09,135.206,152.184,163.396,175.485,186.405,197.119,203.709,218.292,232.82,246.489,260.779,273.981,287.289,298.87,313.909,325.12,337.328,343.936,353.918,360.93,369.82,386.219,403.007,411.926,423.56,432.679,448.729,452.379,474.616,492.63,498.362,502.705,511.267,526.064,540.286,554.363,566.042,581.184,596.486,603.592,612.898,635.625,643.605,658.778,667.785,673.517,681.005,703.37,718.901,721.238,738.96,758.629\"\n" + 
"\"encodedPath\":\"qgglH~l~mVFIHERDHEg@YEUHKZGXOHKGQ_@YEm@OOg@SOQ?QPYVOX@VGTMN[?_@S[QMMYAQDYJEl@BXTNAA_@GUS_@BGf@@^GFI?KIQGe@Ce@Ja@@_@Q_@UWCQNGf@FLHVSNBFIJCZn@VTBALk@Cu@\"\n" + 
"\"encodedLevels\":\"P?A@CAC@@?D@AB@A@D@B?A@DA?@B?D@AADA?D@@@D???B@B@@EA@BA@@C@D?AP\"}}]}";
	
	
	String txt2 = 
"{\"error\":0,\"message\":\"\",\"data\":[{\"trailid\":\"3\",\"vid\":\"4\",\"title\":\"Floppy Bunny\",\"rid\":\"1\",\"difficulty\":\"5\",\"activitytype\":1,\"trailtype\":\"1\",\"biketype\":\"1,2\",\"physical_rating\":\"2\",\"ttfs\":\"16,15\",\"wet_weather\":\"1\",\"season\":\"\",\"unsanctioned\":\"0\",\"hidden\":\"0\",\"rating\":\"81\",\"votes\":\"89\",\"ridden\":\"2256\",\"faved\":\"9\",\"status\":\"1\",\"products\":\"\",\"total_comments\":\"12\",\"total_photos\":\"340\",\"total_videos\":\"42\",\"total_reports\":\"226\",\"total_poi\":\"19\",\"total_supporters\":\"11\",\"total_osmways\":\"1\",\"created\":\"1312869649\",\"changed\":\"1557963188\",\"last_comment_ts\":\"1312869649\",\"last_report_ts\":\"1569305420\",\"last_totals_ts\":\"1569614730\",\"alias\":\"floppy-bunny\",\"approved\":\"1\",\"userid\":\"454369\",\"published\":\"1\",\"dirty\":\"0\",\"views\":\"42576\",\"trackid\":\"121144\",\"condition\":\"4\",\"confirmid\":\"0\",\"download_disable\":\"0\",\"usage\":\"3\",\"direction\":\"2\",\"opendate\":\"0\",\"strava_segment\":\"0\",\"strava_segment_reverse\":\"0\",\"legacy_id\":\"0\",\"closed\":\"0\",\"climb_difficulty\":\"0\",\"land_manager\":\"District of North Vancouver\",\"land_manager_manual\":\"1\",\"refnum\":\"\",\"global_rank\":\"52\",\"global_rank_score\":\"84.888\",\"total_checkins\":\"6503\",\"total_ridelogs\":\"6347\",\"land_manager_url\":\"\",\"aka\":\"\",\"search_terms\":\"\",\"search\":\"Floppy Bunny\",\"featured_photo\":\"0\",\"featured_video\":\"0\",\"skidmap_id\":\"0\",\"osmway\":\"0\",\"password\":\"\",\"funding_goal\":\"1000\",\"funding_usd\":\"0\",\"funding_currency\":\"\",\"watchmen\":\"1\",\"cleanup\":\"0\",\"cleanup2\":\"0\",\"cleanup3\":\"\",\"leaderboard_disable\":\"0\",\"popularity_colour\":\"ff3800\",\"popularity_score\":\"90\",\"season_type\":\"0\",\"alpine\":\"0\",\"ebike\":\"0\",\"color\":\"\",\"archived\":\"0\",\"disable_sensitive_check\":\"0\",\"planned\":\"0\",\"family_friendly\":\"0\",\"total_socialmedia\":\"0\",\"hide_association\":\"0\",\"cover_photo\":\"14704594\",\"trail_association\":\"0\",\"direction_flagged\":\"0\",\"direction_forward\":\"100\",\"direction_backward\":\"0\",\"inventory_exclude\":\"0\",\"difficulty_user_avg\":\"4\",\"difficulty_votes\":\"4\",\"imagemap3\":\"1\",\"connector\":\"0\",\"sac_scale\":\"1\",\"trail_visibility\":\"0\",\"datasource\":\"tf\",\"verified\":\"1\",\"snow_grooming\":\"0\",\"osm_import_job\":\"0\",\"dogs_allowed\":\"1\",\"restricted_access\":\"0\",\"amtb_rating\":\"0\",\"id\":\"3\",\"type\":\"trail\",\"description\":\"Short fast trail with optional jumps and drops. A few mandatory wooden trail features. The trail is great for new riders to sample what they might encounter higher up the mountain.\",\"access_info\":\"Accessed off Mtn. Hwy two trails before the 2nd switchback. Exiting at the water tower/turnaround.\",\"source_text\":\"\",\"source_link\":\"\",\"disclaimer\":\"\",\"amtb_info\":\"\",\"act_mtb\":\"1\",\"act_ebike\":\"0\",\"act_hike\":\"1\",\"act_moto\":\"0\",\"act_trailrun\":\"1\",\"act_atv\":\"0\",\"act_snowshoe\":\"0\",\"act_skialpine\":\"0\",\"act_skixc\":\"0\",\"act_skibc\":\"0\",\"act_horse\":\"0\",\"act_snowmobile\":\"0\",\"act_mototrials\":\"0\",\"latitude\":\"49.359060\",\"longitude\":\"-123.040280\",\"activitytypes\":[1,6,5],\"track\":{\"latitude\":\"49.35906,49.359,49.35889,49.35877,49.35876,49.35877,49.35884,49.3589,49.35885,49.3589,49.35895,49.35903,49.35918,49.35922,49.35925,49.35924,49.35919,49.35913,49.3591,49.35907,49.35907,49.35913,49.35919,49.35925,49.35931,49.3593,49.35931,49.35934,49.35939,49.35949,49.35955,49.35962,49.35968,49.35967,49.35961,49.35958,49.35961,49.35965,49.35975,49.35985,49.3599,49.35995,49.35998,49.35994,49.35987,49.35986,49.35991,49.35998,49.36011,49.36025,49.36031\",\"longitude\":\"-123.04028,-123.0401,-123.03964,-123.03955,-123.03947,-123.03934,-123.03908,-123.03862,-123.03841,-123.03827,-123.03819,-123.03821,-123.03811,-123.03805,-123.03794,-123.03783,-123.03776,-123.03779,-123.03778,-123.03773,-123.03767,-123.0375,-123.03742,-123.03741,-123.03733,-123.03715,-123.0371,-123.03707,-123.03706,-123.03708,-123.03712,-123.03709,-123.03697,-123.03689,-123.03681,-123.0367,-123.03658,-123.03651,-123.03648,-123.03649,-123.03652,-123.03647,-123.03635,-123.03623,-123.03618,-123.03609,-123.03597,-123.03586,-123.03577,-123.0357,-123.03571\",\"altitude\":\"425,424.066,418.173,416.787,415.875,413.958,410.314,403.166,399.205,396.922,395.349,396.182,394.821,393.772,391.799,389.236,387.605,388.077,387.669,386.329,384.931,381.459,379.805,379.921,378.407,374.806,373.991,373.642,373.758,374.69,375.738,375.622,373.874,372.585,371.303,369.573,367.651,367.041,367.06,367.492,368.033,367.334,365.237,363.14,362.49,361.443,358.96,356.565,354.291,352.93,353.041\",\"distance\":\"0,14.635,50.102,64.943,70.839,80.314,100.677,134.632,150.815,162.371,170.396,179.403,197.575,203.789,212.42,220.459,227.978,234.99,238.401,243.321,247.664,261.659,270.49,277.197,286.028,299.103,302.889,306.868,312.471,323.677,330.946,339.022,349.971,355.867,364.698,373.329,382.632,389.372,400.695,411.831,417.796,424.427,433.73,443.487,452.066,458.674,468.984,480.115,495.962,512.324,519.031\",\"encodedPath\":\"cmglHvh~mVJc@T{AVQ@OAYMs@K{AHi@I[IOOB]SGKEU@UHMJDDADI?KKa@KOKAKO@c@AIEEIASBKFMEKW@OJODUEWGMSES@IDIIEWFWLI@QIWMUYQ[MK@\",\"encodedLevels\":\"P?AAC??AE?BA@C?BA?A?CA@?B?C?@?@CA??ACA@?ACA@@AC@A@P\"}},{\"trailid\":\"5\",\"vid\":\"3\",\"title\":\"Pipeline\",\"rid\":\"1\",\"difficulty\":\"5\",\"activitytype\":1,\"trailtype\":\"1\",\"biketype\":\"1,2\",\"physical_rating\":\"2\",\"ttfs\":\"54,5,15,19\",\"wet_weather\":\"1\",\"season\":\"Year round\",\"unsanctioned\":\"0\",\"hidden\":\"0\",\"rating\":\"86\",\"votes\":\"98\",\"ridden\":\"1527\",\"faved\":\"9\",\"status\":\"1\",\"products\":\"\",\"total_comments\":\"4\",\"total_photos\":\"364\",\"total_videos\":\"49\",\"total_reports\":\"179\",\"total_poi\":\"1\",\"total_supporters\":\"3\",\"total_osmways\":\"1\",\"created\":\"1315883527\",\"changed\":\"1547482936\",\"last_comment_ts\":\"1313142894\",\"last_report_ts\":\"1569125069\",\"last_totals_ts\":\"1569382236\",\"alias\":\"pipeline\",\"approved\":\"1\",\"userid\":\"454369\",\"published\":\"1\",\"dirty\":\"0\",\"views\":\"27555\",\"trackid\":\"140967\",\"condition\":\"4\",\"confirmid\":\"0\",\"download_disable\":\"0\",\"usage\":\"2\",\"direction\":\"2\",\"opendate\":\"0\",\"strava_segment\":\"0\",\"strava_segment_reverse\":\"0\",\"legacy_id\":\"0\",\"closed\":\"0\",\"climb_difficulty\":\"0\",\"land_manager\":\"Grouse (top 20m) + District of North Vancouver\",\"land_manager_manual\":\"1\",\"refnum\":\"\",\"global_rank\":\"100\",\"global_rank_score\":\"76.267\",\"total_checkins\":\"2624\",\"total_ridelogs\":\"2751\",\"land_manager_url\":\"\",\"aka\":\"\",\"search_terms\":\"\",\"search\":\"Pipeline\",\"featured_photo\":\"0\",\"featured_video\":\"0\",\"skidmap_id\":\"0\",\"osmway\":\"0\",\"password\":\"\",\"funding_goal\":\"1000\",\"funding_usd\":\"0\",\"funding_currency\":\"\",\"watchmen\":\"1\",\"cleanup\":\"0\",\"cleanup2\":\"0\",\"cleanup3\":\"\",\"leaderboard_disable\":\"0\",\"popularity_colour\":\"ff6b00\",\"popularity_score\":\"80\",\"season_type\":\"0\",\"alpine\":\"0\",\"ebike\":\"0\",\"color\":\"\",\"archived\":\"0\",\"disable_sensitive_check\":\"0\",\"planned\":\"0\",\"family_friendly\":\"0\",\"total_socialmedia\":\"0\",\"hide_association\":\"1\",\"cover_photo\":\"14537621\",\"trail_association\":\"0\",\"direction_flagged\":\"0\",\"direction_forward\":\"100\",\"direction_backward\":\"0\",\"inventory_exclude\":\"0\",\"difficulty_user_avg\":\"5\",\"difficulty_votes\":\"3\",\"imagemap3\":\"1\",\"connector\":\"0\",\"sac_scale\":\"0\",\"trail_visibility\":\"0\",\"datasource\":\"tf\",\"verified\":\"1\",\"snow_grooming\":\"0\",\"osm_import_job\":\"0\",\"dogs_allowed\":\"1\",\"restricted_access\":\"0\",\"amtb_rating\":\"0\",\"id\":\"5\",\"type\":\"trail\",\"description\":\"Contains optional TTFs, steep rough terrain and moderately steep natural TTFs. Quite technical. Definitely very different from the new school trails and a step up in difficulty as well. It is well maintained and can be ridden with a decent amount of speed. A good introduction to the more challenging old-school trails.\",\"access_info\":\"Accessed off Mtn Hwy past the 5th switchback at the large clearing about 20 meters\\r\\npast [L=https://www.trailforks.com/trails/ladies-only/] Ladies Only[/L].\",\"source_text\":\"\",\"source_link\":\"\",\"disclaimer\":\"\",\"amtb_info\":\"\",\"act_mtb\":\"1\",\"act_ebike\":\"0\",\"act_hike\":\"1\",\"act_moto\":\"0\",\"act_trailrun\":\"1\",\"act_atv\":\"0\",\"act_snowshoe\":\"0\",\"act_skialpine\":\"0\",\"act_skixc\":\"0\",\"act_skibc\":\"0\",\"act_horse\":\"0\",\"act_snowmobile\":\"0\",\"act_mototrials\":\"0\",\"latitude\":\"49.358997\",\"longitude\":\"-123.049783\",\"activitytypes\":[1,6,5],\"track\":{\"latitude\":\"49.358997,49.35887,49.3588,49.35868,49.35858,49.35842,49.35823,49.35807,49.35794,49.35778,49.35769,49.35737,49.35723,49.35711,49.357,49.357,49.35703,49.3571,49.35716,49.35716,49.3571,49.35698,49.35664,49.35641,49.35617,49.35601,49.35575,49.35558,49.3555,49.35542,49.35533,49.35529,49.35511,49.35495,49.35479,49.35464,49.35456,49.35432,49.35403,49.3539,49.3538,49.3537,49.35361,49.35351,49.35343,49.35338,49.35319,49.35302,49.3529,49.3527,49.35249,49.35233,49.35221,49.35209,49.35194,49.35161,49.35142\",\"longitude\":\"-123.049783,-123.0496,-123.04953,-123.04954,-123.04961,-123.04965,-123.04953,-123.04934,-123.04926,-123.04913,-123.04901,-123.04825,-123.04803,-123.04798,-123.0479,-123.04775,-123.04763,-123.04747,-123.04716,-123.04706,-123.04689,-123.04679,-123.04696,-123.04691,-123.04674,-123.04672,-123.0466,-123.04636,-123.04614,-123.04597,-123.04583,-123.04571,-123.04547,-123.04536,-123.04539,-123.04554,-123.04566,-123.0462,-123.04694,-123.04718,-123.04729,-123.0473,-123.04728,-123.04719,-123.04705,-123.04674,-123.04619,-123.04597,-123.04577,-123.04553,-123.04537,-123.04533,-123.04535,-123.04534,-123.04526,-123.04517,-123.04514\",\"altitude\":\"588.953,587.007,585.783,585.201,585.434,584.968,582.463,578.915,576.468,573.09,570.296,553.59,546.218,542.768,539.624,536.447,534.211,531.734,526.11,524.772,521.205,518.885,517.02,514.335,510.856,509.331,504.045,499.277,495.316,491.894,489.129,487.703,483.933,482.423,482,481.394,480.228,474.027,471.986,471,469.311,467.039,464.825,461.971,459.291,456.32,449.681,445.718,442.922,439.209,435.135,431.64,428.251,425.047,421.44,412.828,407.061\",\"distance\":\"0,19.355,28.638,41.993,54.206,72.22,95.05,117.528,133.092,153.208,166.454,231.956,254.218,268.035,281.561,292.418,301.721,315.672,339.08,346.318,360.313,375.486,415.222,441.036,470.408,488.247,518.417,544.081,562.319,577.5,591.738,601.495,627.988,647.469,665.381,685.274,697.703,745.022,807.534,830.128,843.799,854.935,865.04,877.921,891.402,914.519,959.584,984.292,1003.975,1032.184,1058.236,1076.25,1089.663,1103.018,1120.664,1157.91,1179.135\",\"encodedPath\":\"ulglHdd`nVVe@LMV@RL^Fd@W^e@XO^YPW~@wCZk@VITO?]EWM_@K}@?SJa@VSbA`@l@In@a@^Cr@W`@o@Nk@Na@P[FWb@o@^U^D\\\\\\\\NVn@jBx@rCXn@RTR@PCRQN[H}@d@mB`@k@Vg@f@o@h@_@^GVBVA\\\\O`AQd@E\",\"encodedLevels\":\"P?C@@C@@?@EBA?D@?@B@DBC@A@DA???B@FA?C?B@AF@AB@B??DB@?A@?P\"}},{\"trailid\":\"6\",\"vid\":\"3\",\"title\":\"Bobsled\",\"rid\":\"1\",\"difficulty\":\"4\",\"activitytype\":1,\"trailtype\":\"2\",\"biketype\":\"1,2,7\",\"physical_rating\":\"1\",\"ttfs\":\"1,54,17,104,21\",\"wet_weather\":\"1\",\"season\":\"year round\",\"unsanctioned\":\"0\",\"hidden\":\"0\",\"rating\":\"90\",\"votes\":\"181\",\"ridden\":\"3572\",\"faved\":\"23\",\"status\":\"1\",\"products\":\"\",\"total_comments\":\"12\",\"total_photos\":\"360\",\"total_videos\":\"156\",\"total_reports\":\"419\",\"total_poi\":\"5\",\"total_supporters\":\"10\",\"total_osmways\":\"1\",\"created\":\"1313143363\",\"changed\":\"1557963384\",\"last_comment_ts\":\"1315895926\",\"last_report_ts\":\"1567966566\",\"last_totals_ts\":\"1569614730\",\"alias\":\"bobsled\",\"approved\":\"1\",\"userid\":\"454369\",\"published\":\"1\",\"dirty\":\"0\",\"views\":\"53774\",\"trackid\":\"114986\",\"condition\":\"5\",\"confirmid\":\"0\",\"download_disable\":\"0\",\"usage\":\"1\",\"direction\":\"1\",\"opendate\":\"0\",\"strava_segment\":\"0\",\"strava_segment_reverse\":\"0\",\"legacy_id\":\"0\",\"closed\":\"0\",\"climb_difficulty\":\"0\",\"land_manager\":\"District of North Vancouver\",\"land_manager_manual\":\"1\",\"refnum\":\"\",\"global_rank\":\"1\",\"global_rank_score\":\"121.769\",\"total_checkins\":\"15455\",\"total_ridelogs\":\"15166\",\"land_manager_url\":\"http://www.dnv.org/recreation-and-leisure\",\"aka\":\"\",\"search_terms\":\"\",\"search\":\"Bobsled\",\"featured_photo\":\"0\",\"featured_video\":\"0\",\"skidmap_id\":\"0\",\"osmway\":\"0\",\"password\":\"\",\"funding_goal\":\"3000\",\"funding_usd\":\"2201.4\",\"funding_currency\":\"CAD\",\"watchmen\":\"1\",\"cleanup\":\"0\",\"cleanup2\":\"0\",\"cleanup3\":\"\",\"leaderboard_disable\":\"0\",\"popularity_colour\":\"ff0500\",\"popularity_score\":\"100\",\"season_type\":\"0\",\"alpine\":\"0\",\"ebike\":\"0\",\"color\":\"\",\"archived\":\"0\",\"disable_sensitive_check\":\"0\",\"planned\":\"0\",\"family_friendly\":\"0\",\"total_socialmedia\":\"0\",\"hide_association\":\"0\",\"cover_photo\":\"12894813\",\"trail_association\":\"0\",\"direction_flagged\":\"0\",\"direction_forward\":\"100\",\"direction_backward\":\"0\",\"inventory_exclude\":\"0\",\"difficulty_user_avg\":\"3\",\"difficulty_votes\":\"11\",\"imagemap3\":\"1\",\"connector\":\"0\",\"sac_scale\":\"0\",\"trail_visibility\":\"0\",\"datasource\":\"tf\",\"verified\":\"1\",\"snow_grooming\":\"0\",\"osm_import_job\":\"0\",\"dogs_allowed\":\"1\",\"restricted_access\":\"0\",\"amtb_rating\":\"0\",\"id\":\"6\",\"type\":\"trail\",\"description\":\"The first sanctioned new school flow trail, was rebuilt by the District of North Vancouver Trail crew in 2010. The proof is in the pudding of its popularity.  On a nice day, you literally need to take a number for the flow show: big berms, small doubles and buff, undulating trail will have you climbing back up for another lap.  Barely 10 minutes up [L=https://www.trailforks.com/trails/mountain-highway/]Mountain Highway[/L], it is easily accessed.  It's importance lies not only in its popularity, but it proves new school trails can be built on the Shore.\\r\\n\\r\\nEntirely pumped and some good carved berms. Has a wood wall ride, that must be ridden cautiously when wet. \\r\\nExtensive work has been invested in the trail via the NSMBA TAP program including a significant reroute near the bottom to bring the grade in line with the goal of the trail to serve beginners.\",\"access_info\":\"Accessed before the 2nd switchback, exits above the [L=https://www.trailforks.com/trails/baden-powell/]Baden Powell[/L].\",\"source_text\":\"\",\"source_link\":\"\",\"disclaimer\":\"\",\"amtb_info\":\"\",\"act_mtb\":\"1\",\"act_ebike\":\"0\",\"act_hike\":\"0\",\"act_moto\":\"0\",\"act_trailrun\":\"0\",\"act_atv\":\"0\",\"act_snowshoe\":\"0\",\"act_skialpine\":\"0\",\"act_skixc\":\"0\",\"act_skibc\":\"0\",\"act_horse\":\"0\",\"act_snowmobile\":\"0\",\"act_mototrials\":\"0\",\"latitude\":\"49.358170\",\"longitude\":\"-123.040960\",\"activitytypes\":[1],\"track\":{\"latitude\":\"49.35817,49.35813,49.35808,49.35798,49.35793,49.35813,49.35816,49.35811,49.357977,49.35784,49.35779,49.35783,49.35799,49.35802,49.358106,49.35821,49.358302,49.35838,49.358389,49.358291,49.35817,49.358047,49.35792,49.357812,49.35773,49.35773,49.35783,49.35792,49.35799,49.358,49.35797,49.35791,49.35783,49.357683,49.35755,49.35747,49.35748,49.35752,49.35762,49.3576,49.3574,49.35724,49.3572,49.3572,49.35725,49.357292,49.357316,49.35725,49.357243,49.35733,49.357442,49.357466,49.357386,49.357183,49.357119,49.356999,49.356919,49.356879,49.356812,49.356679,49.356559,49.356539,49.356469,49.356489\",\"longitude\":\"-123.04096,-123.04091,-123.04088,-123.04091,-123.04088,-123.04075,-123.04064,-123.04058,-123.04054,-123.04046,-123.0404,-123.04031,-123.04018,-123.03995,-123.039869,-123.03982,-123.039767,-123.03968,-123.03959,-123.039456,-123.03938,-123.039381,-123.03935,-123.039274,-123.03914,-123.03898,-123.03884,-123.03877,-123.03864,-123.03855,-123.03842,-123.03839,-123.03839,-123.03841,-123.03852,-123.03851,-123.03835,-123.03824,-123.03808,-123.03804,-123.03805,-123.03801,-123.03796,-123.0379,-123.03781,-123.037616,-123.037423,-123.037257,-123.037096,-123.036935,-123.036812,-123.036721,-123.036683,-123.036721,-123.036771,-123.036671,-123.036691,-123.036641,-123.03663,-123.036861,-123.036971,-123.036961,-123.036741,-123.036471\",\"altitude\":\"432.449,430.9,429.619,429.152,428.072,427.208,425.118,423.704,421.524,419.243,417.841,416.863,416.261,414.036,414.718,414.786,414.562,414.525,413.788,411.344,408.495,406.802,404.545,401.317,397.673,394.876,393.332,393.527,392.478,390.678,387.295,385.902,384.77,382.939,382.692,381.119,377.942,376.549,375.225,374.177,370.731,366.634,364.823,363.89,363.703,361.419,358.431,353.265,351.226,350.26,351.329,350.745,348.257,344.768,344.401,337.703,335.107,332.777,330.505,333.035,333.072,332.18,324.169,318.529\",\"distance\":\"0,5.732,11.697,23.02,28.985,53.12,61.751,68.803,83.864,100.152,107.204,115.09,135.206,152.184,163.396,175.485,186.405,197.119,203.709,218.292,232.82,246.489,260.779,273.981,287.289,298.87,313.909,325.12,337.328,343.936,353.918,360.93,369.82,386.219,403.007,411.926,423.56,432.679,448.729,452.379,474.616,492.63,498.362,502.705,511.267,526.064,540.286,554.363,566.042,581.184,596.486,603.592,612.898,635.625,643.605,658.778,667.785,673.517,681.005,703.37,718.901,721.238,738.96,758.629\",\"encodedPath\":\"qgglH~l~mVFIHERDHEg@YEUHKZGXOHKGQ_@YEm@OOg@SOQ?QPYVOX@VGTMN[?_@S[QMMYAQDYJEl@BXTNAA_@GUS_@BGf@@^GFI?KIQGe@Ce@Ja@@_@Q_@UWCQNGf@FLHVSNBFIJCZn@VTBALk@Cu@\",\"encodedLevels\":\"P?A@CAC@@?D@AB@A@D@B?A@DA?@B?D@AADA?D@@@D???B@B@@EA@BA@@C@D?AP\"}}]}";
	@Test
	public void fullTest() {
		mus.run();
	}
	
	@Autowired
	TrailsSrv ts;
	//@Test
	public void jsonTest() throws IOException {
		List<TrailData> res = ts.ParseTrailsResponse(txt2);
		if (res.size()!=3) {
			throw new RuntimeException(String.format("Liczba elementów %i, powinno być 3.", res.size()));
		}	
	}

	//@Test
//	public void contextLoads() {
//	}
	
	//@Test
//	public void httpGetLines() {
//		try {
//			List<String> pageLines = hrs.getPage("http://192.168.1.1/");
//			int i = 0;
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}	
	

	//Nieużywane
//	private boolean download(Path tmpFilePath) {
//		boolean exists;
//		try {
//			String fileUrl = "https://www.nuug.no/pub/openstreetmap/frikart/garmin/poland/roadmap/windows/OSM_Roadmap_Poland.exe";
//			
//			if (!Files.exists(tmpFilePath, java.nio.file.LinkOption.NOFOLLOW_LINKS)) {			
//				exists = ds.fileDownload(
//						fileUrl, 
//						tmpFilePath.toString()
//						);
//			}
//			else {
//				exists = true;
//			}
//			return exists;
//		}
//		catch (Exception e) {
//			int t = 0;
//			return false;
//		}
//	}
	
//	private boolean unzip(Path tmpFilePath, Path tmpTmpDirPath) {
//		try {			
//			us.unzip7Zip(tmpFilePath.toString(), tmpTmpDirPath);
////			ds.unzipArchive(tmpFilePath.toString(), tmpTmpDirPath.toString());
////			ds.unzipZip4J(tmpFilePath.toString(), tmpTmpDirPath.toString());
//		}
//		catch (Exception e) {
//			System.out.println("Unzip error: "+e.getMessage());
//			return false;
//		}
//		System.out.println(" -- 'unzip' done --");
//		return true;
//	}
	
	//Nieużywane
//	private boolean addMapToMapSource() {
//		try {			
//			gs.addToMapSource(
//					fenv.getTypFid(), 
//					fenv.getDstMapSourceDir(), 
//					fenv.getDstMapName(), 
//					fenv.getScrImgs(), 
//					fenv.getSrcTypFilePath(),
//					fenv.getPriority(),
//					fenv.getTransparency()
//					);
//			return true;
//		}
//		catch (Exception e) {
//			System.out.println("GMTSrv error: "+e.getMessage());
//			return false;
//		}
//	}
	
//	private boolean addMapToCard() {
//		String t = fenv.getDstMapNumber();
//		System.out.println(t);
//		t = t;
//		try {			
//			gs.addToCard(
//					fenv.getTypFid(), 
//					fenv.getDstCardPath(), 
//					fenv.getDstMapName(), 
//					fenv.getScrImgs(), 
//					fenv.getSrcTypFilePath(),
//					fenv.getPriority(),
//					fenv.getTransparency()
//					);
//			return true;
//		}
//		catch (Exception e) {
//			System.out.println("GMTSrv error: "+e.getMessage());
//			return false;
//		}
//	}
	
//	@Test
//	public void chainTest() {
//		
//		boolean goNext = true;
//		if(! fenv.getUsePreparedTmpLocation()){
//			/*
//			 * Sprawdzanie istnienia pliku ze zródłową mapą
//			 */
//			File f = new File(fenv.getDownloadedFile());
//			goNext = f.exists();	
//			if (!goNext){
//				System.out.println("Brak pliku '"+fenv.getDownloadedFile()+"'");
//				return;
//			}
//			/*
//			 * unzip
//			 */
//			goNext = unzip(Paths.get(fenv.getDownloadedFile()), Paths.get(fenv.getTmpLocation()));
//			if (!goNext){
//				System.out.println("Nie powiodła się akcja rozpakowywania.");
//				return;	
//			}
//		}
//		
//		/*
//		 * Tworzenie mapy
//		 */
//		if (fenv.getKdDst() > 3) {
//			System.out.println("Niezdefiniowana akcja o kodzie '" + fenv.getKdDst() + "'");
//			return;
//		}		
////		if (fenv.getKdDst() == 1 || fenv.getKdDst() == 3) {
////			goNext =  addMapToMapSource();
////			if (!goNext){
////				System.out.println("Nie powiodła się akcja instalacji mapy");
////				return;	
////			}
////		}
//		if (fenv.getKdDst() == 2 || fenv.getKdDst() == 3) {
//			goNext =  addMapToCard();
//			if (!goNext){
//				System.out.println("Nie powiodła się akcja utworzenia mapy");
//				return;	
//			}
//		}
//	}

}
