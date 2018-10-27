package event

trait Event extends Serializable {
  def name: String = this.getClass.getSimpleName.replace("$", "")
}