package under.the.bridge.util.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by lam on 2/6/17.
 */

class ComputationMainScheduler<T> protected constructor() : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())
