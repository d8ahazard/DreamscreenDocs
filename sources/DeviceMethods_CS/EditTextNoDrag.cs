namespace com.lab714.dreamscreenv2.devices
{
	using Context = android.content.Context;
	using AppCompatEditText = androidx.appcompat.widget.AppCompatEditText;
	using AttributeSet = android.util.AttributeSet;
	using DragEvent = android.view.DragEvent;

	public class EditTextNoDrag : AppCompatEditText
	{
		public EditTextNoDrag(Context context) : base(context)
		{
		}

		public EditTextNoDrag(Context context, AttributeSet attrs) : base(context, attrs)
		{
		}

		public EditTextNoDrag(Context context, AttributeSet attrs, int defStyleAttr) : base(context, attrs, defStyleAttr)
		{
		}

		public virtual bool onDragEvent(DragEvent @event)
		{
			return false;
		}
	}

}