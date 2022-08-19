package com.mojang.realmsclient.gui;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.gui.task.RepeatableTask;
import com.mojang.realmsclient.util.RealmsPersistence;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsDataFetcher {
   private static final Logger f_87794_ = LogUtils.getLogger();
   private final Minecraft f_167329_;
   private final RealmsClient f_167330_;
   private final ScheduledExecutorService f_87795_ = Executors.newScheduledThreadPool(3);
   private volatile boolean f_87796_ = true;
   private final RepeatableTask serverListUpdateTask = RepeatableTask.m_167587_(this::m_167344_, Duration.ofSeconds(60L), this::m_87860_);
   private final RepeatableTask liveStatsTask = RepeatableTask.m_167587_(this::m_167347_, Duration.ofSeconds(10L), this::m_87860_);
   private final RepeatableTask f_87798_ = RepeatableTask.m_167581_(this::m_167345_, Duration.ofSeconds(10L), this::m_87860_);
   private final RepeatableTask trialAvailabilityTask = RepeatableTask.m_167581_(this::m_167346_, Duration.ofSeconds(60L), this::m_87860_);
   private final RepeatableTask f_87801_ = RepeatableTask.m_167581_(this::m_167348_, Duration.ofMinutes(5L), this::m_87860_);
   private final RealmsPersistence f_167331_;
   private final Set<RealmsServer> f_87802_ = Sets.newHashSet();
   private List<RealmsServer> f_87803_ = Lists.newArrayList();
   private RealmsServerPlayerLists f_87804_;
   private int f_87805_;
   private boolean f_87806_;
   private boolean f_87807_;
   private String f_87808_;
   private ScheduledFuture<?> f_87809_;
   private ScheduledFuture<?> f_87810_;
   private ScheduledFuture<?> f_87811_;
   private ScheduledFuture<?> f_87812_;
   private ScheduledFuture<?> f_87813_;
   private final Map<RealmsDataFetcher.Task, Boolean> f_87814_ = new ConcurrentHashMap<>(RealmsDataFetcher.Task.values().length);

   public RealmsDataFetcher(Minecraft p_167333_, RealmsClient p_167334_) {
      this.f_167329_ = p_167333_;
      this.f_167330_ = p_167334_;
      this.f_167331_ = new RealmsPersistence();
   }

   @VisibleForTesting
   protected RealmsDataFetcher(Minecraft p_167336_, RealmsClient p_167337_, RealmsPersistence p_167338_) {
      this.f_167329_ = p_167336_;
      this.f_167330_ = p_167337_;
      this.f_167331_ = p_167338_;
   }

   public boolean m_87817_() {
      return this.f_87796_;
   }

   public synchronized void m_87841_() {
      if (this.f_87796_) {
         this.f_87796_ = false;
         this.m_87859_();
         this.m_87858_();
      }

   }

   public synchronized void m_87847_() {
      if (this.f_87796_) {
         this.f_87796_ = false;
         this.m_87859_();
         this.f_87814_.put(RealmsDataFetcher.Task.PENDING_INVITE, false);
         this.f_87810_ = this.f_87798_.m_167585_(this.f_87795_);
         this.f_87814_.put(RealmsDataFetcher.Task.TRIAL_AVAILABLE, false);
         this.f_87811_ = this.trialAvailabilityTask.m_167585_(this.f_87795_);
         this.f_87814_.put(RealmsDataFetcher.Task.UNREAD_NEWS, false);
         this.f_87813_ = this.f_87801_.m_167585_(this.f_87795_);
      }

   }

   public boolean m_87820_(RealmsDataFetcher.Task p_87821_) {
      Boolean obool = this.f_87814_.get(p_87821_);
      return obool != null && obool;
   }

   public void m_87848_() {
      this.f_87814_.replaceAll((p_167340_, p_167341_) -> {
         return false;
      });
   }

   public synchronized void m_87849_() {
      this.m_87856_();
      this.m_87841_();
   }

   public synchronized List<RealmsServer> m_87850_() {
      return ImmutableList.copyOf(this.f_87803_);
   }

   public synchronized int m_87851_() {
      return this.f_87805_;
   }

   public synchronized boolean m_87852_() {
      return this.f_87806_;
   }

   public synchronized RealmsServerPlayerLists m_87853_() {
      return this.f_87804_;
   }

   public synchronized boolean m_87854_() {
      return this.f_87807_;
   }

   public synchronized String m_87855_() {
      return this.f_87808_;
   }

   public synchronized void m_87856_() {
      this.f_87796_ = true;
      this.m_87859_();
   }

   private void m_87858_() {
      for(RealmsDataFetcher.Task realmsdatafetcher$task : RealmsDataFetcher.Task.values()) {
         this.f_87814_.put(realmsdatafetcher$task, false);
      }

      this.f_87809_ = this.serverListUpdateTask.m_167585_(this.f_87795_);
      this.f_87810_ = this.f_87798_.m_167585_(this.f_87795_);
      this.f_87811_ = this.trialAvailabilityTask.m_167585_(this.f_87795_);
      this.f_87812_ = this.liveStatsTask.m_167585_(this.f_87795_);
      this.f_87813_ = this.f_87801_.m_167585_(this.f_87795_);
   }

   private void m_87859_() {
      Stream.of(this.f_87809_, this.f_87810_, this.f_87811_, this.f_87812_, this.f_87813_).filter(Objects::nonNull).forEach((p_167343_) -> {
         try {
            p_167343_.cancel(false);
         } catch (Exception exception) {
            f_87794_.error("Failed to cancel Realms task", (Throwable)exception);
         }

      });
   }

   private synchronized void m_87839_(List<RealmsServer> p_87840_) {
      int i = 0;

      for(RealmsServer realmsserver : this.f_87802_) {
         if (p_87840_.remove(realmsserver)) {
            ++i;
         }
      }

      if (i == 0) {
         this.f_87802_.clear();
      }

      this.f_87803_ = p_87840_;
   }

   public synchronized List<RealmsServer> m_210672_(RealmsServer p_210673_) {
      this.f_87803_.remove(p_210673_);
      this.f_87802_.add(p_210673_);
      return ImmutableList.copyOf(this.f_87803_);
   }

   private boolean m_87860_() {
      return !this.f_87796_;
   }

   private void m_167344_() {
      try {
         List<RealmsServer> list = this.f_167330_.listWorlds().servers;
         if (list != null) {
            list.sort(new RealmsServer.McoServerComparator(this.f_167329_.getUser().getName()));
            this.m_87839_(list);
            this.f_87814_.put(RealmsDataFetcher.Task.SERVER_LIST, true);
         } else {
            f_87794_.warn("Realms server list was null");
         }
      } catch (Exception exception) {
         this.f_87814_.put(RealmsDataFetcher.Task.SERVER_LIST, true);
         f_87794_.error("Couldn't get server list", (Throwable)exception);
      }

   }

   private void m_167345_() {
      try {
         this.f_87805_ = this.f_167330_.pendingInvitesCount();
         this.f_87814_.put(RealmsDataFetcher.Task.PENDING_INVITE, true);
      } catch (Exception exception) {
         f_87794_.error("Couldn't get pending invite count", (Throwable)exception);
      }

   }

   private void m_167346_() {
      try {
         this.f_87806_ = this.f_167330_.trialAvailable();
         this.f_87814_.put(RealmsDataFetcher.Task.TRIAL_AVAILABLE, true);
      } catch (Exception exception) {
         f_87794_.error("Couldn't get trial availability", (Throwable)exception);
      }

   }

   private void m_167347_() {
      try {
         this.f_87804_ = this.f_167330_.getLiveStats();
         this.f_87814_.put(RealmsDataFetcher.Task.LIVE_STATS, true);
      } catch (Exception exception) {
         f_87794_.error("Couldn't get live stats", (Throwable)exception);
      }

   }

   private void m_167348_() {
      try {
         RealmsPersistence.RealmsPersistenceData realmspersistence$realmspersistencedata = this.m_167349_();
         this.f_87807_ = realmspersistence$realmspersistencedata.hasUnreadNews;
         this.f_87808_ = realmspersistence$realmspersistencedata.newsLink;
         this.f_87814_.put(RealmsDataFetcher.Task.UNREAD_NEWS, true);
      } catch (Exception exception) {
         f_87794_.error("Couldn't update unread news", (Throwable)exception);
      }

   }

   private RealmsPersistence.RealmsPersistenceData m_167349_() {
      RealmsPersistence.RealmsPersistenceData realmspersistence$realmspersistencedata;
      try {
         RealmsNews realmsnews = this.f_167330_.getNews();
         realmspersistence$realmspersistencedata = new RealmsPersistence.RealmsPersistenceData();
         realmspersistence$realmspersistencedata.newsLink = realmsnews.newsLink;
      } catch (Exception exception) {
         f_87794_.warn("Failed fetching news from Realms, falling back to local cache", (Throwable)exception);
         return this.f_167331_.read();
      }

      RealmsPersistence.RealmsPersistenceData realmspersistence$realmspersistencedata1 = this.f_167331_.read();
      boolean flag = realmspersistence$realmspersistencedata.newsLink == null || realmspersistence$realmspersistencedata.newsLink.equals(realmspersistence$realmspersistencedata1.newsLink);
      if (flag) {
         return realmspersistence$realmspersistencedata1;
      } else {
         realmspersistence$realmspersistencedata.hasUnreadNews = true;
         this.f_167331_.save(realmspersistence$realmspersistencedata);
         return realmspersistence$realmspersistencedata;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Task {
      SERVER_LIST,
      PENDING_INVITE,
      TRIAL_AVAILABLE,
      LIVE_STATS,
      UNREAD_NEWS;
   }
}