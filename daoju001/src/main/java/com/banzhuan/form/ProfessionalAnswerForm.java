package com.banzhuan.form;

import java.io.Serializable;

import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.QuestionEntity;

public class ProfessionalAnswerForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1441369994745117280L;

	private QuestionEntity question;
	private ProfessionalAnswerEntity answer;
	
	private boolean hasEdit;
	
	/**
	 * @return the question
	 */
	public QuestionEntity getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public ProfessionalAnswerEntity getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(ProfessionalAnswerEntity answer) {
		this.answer = answer;
	}
	/**
	 * @return the hasEdit
	 */
	public boolean isHasEdit() {
		return hasEdit;
	}
	/**
	 * @param hasEdit the hasEdit to set
	 */
	public void setHasEdit(boolean hasEdit) {
		this.hasEdit = hasEdit;
	}

}
