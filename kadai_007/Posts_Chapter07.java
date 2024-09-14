package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement statement = null;
		String[][] postsList = {
				{"1003","2023-02-08","昨日の夜は徹夜でした・・","13"},
				{"1002","2023-02-08","お疲れ様です！","12"},
				{"1003","2023-02-09","今日も頑張ります！","18"},
				{"1001","2023-02-09","無理は禁物ですよ！","17"},
				{"1002","2023-02-10","明日から連休ですね！","20"}
		};
		
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Soule7876!"
					);
			
			System.out.println("データベース接続成功:" + con);
			
			String sql = "INSERT INTO posts(user_id,posted_at,post_content,likes) VALUES(?,?,?,?);";
			statement = con.prepareStatement(sql);
			
			System.out.println("レコードの追加を実行します");
			
			int rowCnt=0;
			for( int i =0; i < postsList.length;i++ ) {
				statement.setString(1,postsList[i][0]);
				statement.setString(2,postsList[i][1]);
				statement.setString(3,postsList[i][2]);
				statement.setString(4,postsList[i][3]);
				rowCnt += statement.executeUpdate();
			}
			System.out.println( rowCnt+"件のレコードが追加されました");
		try(Statement statement2 = con.createStatement()){
			
			String sql2 = "SELECT * FROM posts WHERE user_id = 1002;";
			
			ResultSet result = statement.executeQuery(sql2);
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			int countNumber = 0;
			
			while(result.next()) {
				Date postTime = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int likeNumber = result.getInt("likes");
				
				countNumber++;
				
				System.out.println(countNumber +"件目:投稿日時"+ 
									postTime + "/投稿内容=" + postContent +"/いいね数=" + likeNumber);
				
			}
			
		}
			
					
		}catch(SQLException e) {
			System.out.println("エラー発生:"+ e.getMessage());
		}finally {
			if( statement != null) {
				try { statement.close();} catch(SQLException ignore){}
			}
				if(con != null) {
					try { con.close();}catch(SQLException ignore) {}
				}
		}

	}

}
