package mrs.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * @Entity→付与されたクラスがエンティティクラスであることを示すアノテーション
 */
@Entity
public class MeetingRoom implements Serializable {
	/*
	 * @Id→プライマリーキーを指定する。エンティティクラス定義時は必ず用意する。レコード毎の識別子。
	 * @GeneratedValue→プライマリーキーのフィールドに対して値を自動生成する
	 * 引数のstrategyで生成方法を指定。GenerationTypeという列挙型の値を指定する。
	 * IDENTITYを生成することで、主キー値を生成する
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomId;

	private String roomName;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}
