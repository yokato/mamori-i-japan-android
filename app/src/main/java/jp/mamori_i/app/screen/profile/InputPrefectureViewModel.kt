package jp.mamori_i.app.screen.profile

import android.app.Activity
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import jp.mamori_i.app.data.model.PrefectureType
import jp.mamori_i.app.data.model.Profile
import jp.mamori_i.app.screen.common.MIJError
import jp.mamori_i.app.screen.common.MIJError.Reason.*
import jp.mamori_i.app.screen.common.MIJError.Action.*
import jp.mamori_i.app.data.repository.profile.ProfileRepository
import jp.mamori_i.app.screen.common.LogoutHelper
import jp.mamori_i.app.screen.start.AgreementTransitionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class InputPrefectureViewModel(private val profileRepository: ProfileRepository,
                               private val logoutHelper: LogoutHelper,
                               private val disposable: CompositeDisposable): ViewModel() {

    lateinit var navigator: InputPrefectureNavigator
    val updateError = PublishSubject.create<MIJError>()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    fun onClickNextButton(inputPrefecture: PrefectureType) {
        navigator.goToAgreement(AgreementTransitionEntity(inputPrefecture))
    }

    fun onClickUpdateButton(inputPrefecture: PrefectureType,
                            activity: Activity) {
        navigator.finishWithCompleteMessage("更新しました。") // TODO メッセージ
        /*
        profileRepository.updateProfile(profile, activity)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    navigator.hideProgress()
                    navigator.finishWithCompleteMessage("更新しました。") // TODO メッセージ
                },
                onError = { e ->
                    navigator.hideProgress()
                    val reason = MIJError.mappingReason(e)
                    if (reason == Auth) {
                        // 認証エラーの場合はログアウト処理をする
                        runBlocking (Dispatchers.IO) {
                            logoutHelper.logout()
                        }
                    }
                    updateError.onNext(
                        when (reason) {
                            NetWork -> MIJError(reason, "文言検討1", DialogCloseOnly)
                            Auth -> MIJError(reason, "文言検討22", DialogLogout)
                            else -> MIJError(reason, "文言検討2", DialogCloseOnly)
                        })
                }
            ).addTo(disposable)*/
    }
}